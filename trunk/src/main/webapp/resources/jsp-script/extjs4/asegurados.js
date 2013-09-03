///////////////////////////////////////
////// ini catalogos de tatrisit //////
///////////////////////////////////////
var comboGeneros,storeGeneros;                                      //1
//fecha nacimiento                                                    2
var campoCodigoPostal;                                              //3
var comboEstados,storeEstados;                                      //4
//var comboCiudad,storeCiudades;                                      //4 X(
var comboDeducible,storeDeducible;                                  //5
var comboCopago,storeCopagos;                                       //6
var comboSumaAsegurada,storeSumasAseguradas;                        //7
var comboCirculoHospitalario,storeCirculoHospitalario;              //8
var comboCoberturaVacunas,storeCoberturaVacunas;                    //9
var comboCoberturaPrevencionEnfermedadesAdultos,
        storeCoberturaPrevencionEnfermedadesAdultos;                //10
var comboMaternidad,storeMaternidad;                                //11
var comboSumaAseguradaMaternidad,storeSumaAseguradaMaternidad;      //12
var comboBaseTabuladorReembolso,storeBaseTabuladorReembolso;        //13
var comboCostoEmergenciaExtranjero,storeCostoEmergenciaExtranjero;  //14
var comboCobElimPenCambioZona,storeCobElimPenCambioZona;            //15
var comboRoles,storeRoles;                                          //16
///////////////////////////////////////
////// fin catalogos de tatrisit //////
///////////////////////////////////////

var storeIncisos;
var formPanel;
var gridIncisos;
var gridResultados,storeResultados;
var botonVerCoberturas;
var botonComprar;

//window coberturas
var windowCoberturas;
var coberturasFormPanel;
var storeCoberturas;
var gridCoberturas;
var windowAyudaCobertura;
var botonVerDetalleCobertura;

//grid selection
var selected_prima;
var selected_cd_plan;
var selected_ds_plan;
var selected_nm_plan;
var selected_record;
var selected_cobertura;

function detallesCobertura()
{
    windowCoberturas.setLoading(true);
    Ext.Ajax.request(
    {
        url: _URL_DETALLE_COBERTURA,
        params:{
            idCobertura:selected_cobertura.get('cdGarant'),
            idCiaAseguradora:selected_record.get('cdCiaaseg'),//del grid de resultados
            idRamo:selected_record.get('cdRamo')//del grid de resultados
        },
        success: function(response, opts)
        {
            //Ext.MessageBox.hide();
            windowCoberturas.setLoading(false);
            var jsonResp = Ext.decode(response.responseText);
            if(jsonResp.ayudaCobertura
                &&jsonResp.ayudaCobertura.dsGarant
                &&jsonResp.ayudaCobertura.dsGarant.length>0
                &&jsonResp.ayudaCobertura.dsAyuda
                &&jsonResp.ayudaCobertura.dsAyuda.length>0)
            {
                windowAyudaCobertura.html='<table width=430 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:11px;"><b>'+jsonResp.ayudaCobertura.dsGarant+'</b></td></tr><tr><td style="font-size:11px; ">'+jsonResp.ayudaCobertura.dsAyuda+'</td></tr></table>';
                windowAyudaCobertura.show();
            }
            else
            {
                Ext.Msg.show({
                    title: 'No hay informaci&oacute;n',
                    msg: 'No hay informaci&oacute;n de la cobertura',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.ERROR
                });
            }
        },
        failure: function(response, opts)
        {
            windowCoberturas.setLoading(false);
            Ext.Msg.show({
                title: 'Error',
                msg: 'Error de comunicaci&oacute;n',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });
}

function bloquearFormulario(isBloqueado)
{
    formPanel.getForm().getFields().each(function(field,index){
        field.setReadOnly(isBloqueado);
    });
    Ext.getCmp('botonLimpiar').setDisabled(isBloqueado);
    Ext.getCmp('botonCotizar').setDisabled(isBloqueado);
    Ext.getCmp('idCotizacion').setReadOnly(true);
    Ext.getCmp('fechaFinVigencia').setReadOnly(true);
    gridIncisos.setDisabled(isBloqueado);
}

///////////////////////////////////////////////////////
////// funcion que muestra el grid de resultados //////
/*///////////////////////////////////////////////////*/
function mostrarGrid()
{
    bloquearFormulario(true);
    gridResultados.show();
    window.parent.scrollTo(0,800);
    storeResultados.load({
        callback:function(){
            //window.console&&console.log("cotizado");
            gridResultados.getView().refresh();
        }
    });
}
/*///////////////////////////////////////////////////*/
////// funcion que muestra el grid de resultados //////
///////////////////////////////////////////////////////

Ext.onReady(function(){
    
    //////////////////////////////
    ////// Inicio de stores //////
    //////////////////////////////
    
    //1 sexo (GRID)
    storeGeneros = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_SEXO},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //4 estados
    storeEstados = new Ext.data.Store({
        model: 'Generic',
        autoLoad:false,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_ESTADO},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }/*,
        sorters:
        [
            {
                property : 'value',
                direction: 'ASC'
            }
        ]*/
    });
    
    /*4 ciudades X( 
    storeCiudades = new Ext.data.Store({
        model: 'Generic',
        autoLoad:false,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        },
        sorters:
        [
            {
                property : 'value',
                direction: 'ASC'
            }
        ]
    });*/
    
    //5 deducible
    storeDeducible = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_DEDUCIBLE},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //6 copago
    storeCopagos = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_COPAGO},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //7 suma asegurada
    storeSumasAseguradas = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_SUMA_ASEGURADA},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //8 circulo hospitalario
    storeCirculoHospitalario = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_CIRCULO_HOSPITALARIO},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }/*,
        sorters:
        [
            {
                property : 'value',
                direction: 'ASC'
            }
        ]*/
    });
    
    //9 cobertura vacunas
    storeCoberturaVacunas = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_COBERTURA_VACUNAS},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //10 enfemedades adultos
    storeCoberturaPrevencionEnfermedadesAdultos = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_COBERTURA_PREV_ENF_ADULTOS},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //11 maternidad
    storeMaternidad = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_MATERNIDAD},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //12 suma maternidad
    storeSumaAseguradaMaternidad = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_SUMA_ASEGUARADA_MATERNIDAD},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //13 base tabulador reembolso
    storeBaseTabuladorReembolso = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_BASE_TABULADOR_REEMBOLSO},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //14 emergencia extranjero
    storeCostoEmergenciaExtranjero = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_COSTO_EMERGENCIA_EXTRANJERO},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //15 cobertura eliminacion penalizaacion cambio zona
    storeCobElimPenCambioZona = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_COB_ELIM_PEN_CAMBIO_ZONA},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    //16 roles (GRID)
    storeRoles = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_OBTEN_CATALOGO_GENERICO,
            extraParams:{cdatribu:CDATRIBU_ROL},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeIncisos=new Ext.data.Store(
    {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'IncisoSalud'/*,
        data:
        [
            {
                rol:new Generic({'key':'20','value':'Tomador'}),
                fechaNacimiento:new Date(),
                sexo:new Generic({'key':'H','value':'Hombre'}),
                nombre:'Alvaro',
                segundoNombre:'Jair',
                apellidoPaterno:'Martínez',
                apellidoMaterno:'Varela'
            },
            {
                rol:new Generic({'key':'20','value':'Tomador'}),
                fechaNacimiento:new Date(),
                sexo:new Generic({'key':'H','value':'Hombre'}),
                nombre:'Ricardo',
                segundoNombre:'',
                apellidoPaterno:'Bautista',
                apellidoMaterno:'Silva'
            }
        ]*/
    });
    
    /////////////////////////////
    ////// Store resutados //////
    /*/////////////////////////*/
    Ext.define('StoreResultados', {
        extend: 'Ext.data.Store',
        constructor: function(cfg) {
            var me = this;
            cfg = cfg || {};
            me.callParent([Ext.apply({
                autoLoad: false,
                model: 'RowCotizacion',
                storeId: 'StoreResultados',
                //groupField: 'dsUnieco',
                proxy: {
                    type: 'ajax',
                    url: _URL_RESULTADOS,
                    reader: {
                        type: 'json',
                        root: 'dataResult'
                    }
                }
            }, cfg)]);
        }
    });
    storeResultados=new StoreResultados();
    /*/////////////////////////*/
    ////// Store resutados //////
    /////////////////////////////
    
    /////////////////////////////
    ////// storeCoberturas //////
    /*/////////////////////////*/
    Ext.define('StoreCoberturas', {
        extend: 'Ext.data.Store',
        constructor: function(cfg) {
            var me = this;
            cfg = cfg || {};
            me.callParent([Ext.apply({
                autoLoad: false,
                model: 'RowCobertura',
                storeId: 'StoreCoberturas',
                //groupField: 'dsUnieco',
                proxy: {
                    type: 'ajax',
                    url: _URL_COBERTURAS,
                    reader: {
                        type: 'json',
                        root: 'listaCoberturas'
                    }
                }
            }, cfg)]);
        }
    });
    storeCoberturas=new StoreCoberturas();
    /*/////////////////////////*/
    ////// storeCoberturas //////
    /////////////////////////////
    
    ///////////////////////////
    ////// Fin de stores //////
    ///////////////////////////
    
    ///////////////////////////////////////////////////////////
    ////// ComboBox que solo manda el key sin ser objeto //////
    ///////////////////////////////////////////////////////////
    Ext.define('Ext.form.ComboBox2',
    {
        extend:'Ext.form.ComboBox',
        setValue: function(v)
        {
            if(v&&v.key&&v.value)
            {
                this.setValue(v.key);
            }
            else
            {
                this.callOverridden(arguments);
            }
        }
    });
    //////////////////////////////////////////////////////////////////
    ////// Fin de comboBox que solo manda el key sin ser objeto //////
    //////////////////////////////////////////////////////////////////
    
    //////////////////////////////////////////////////////////////////////
    ////// Inicio de combos de formulario y combos editores de grid //////
    //////////////////////////////////////////////////////////////////////
    
    //1 sexo (GRID)
    comboGeneros=Ext.create('Ext.form.ComboBox',
    {
        id:'comboGeneros',
        store: storeGeneros,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false
    });
    
    //3 codigo postal
    campoCodigoPostal=Ext.create('Ext.form.field.Text',{
        fieldLabel: 'C&oacute;digo postal',
        name:'codigoPostal',
        allowBlank:false,
        maskRe: /[0-9]/,
        regex:/[0-9]/,
        listeners:
        {
            blur:function(el)
            {
                comboEstados.setLoading(true);
                storeEstados.load({
                    params:
                    {
                        //codigoTabla:'2TMUNI',
                        idPadre:el.value
                    },
                    callback: function(records, operation, success)
                    {
                        var estadoActual=comboEstados.getValue();
                        var actualEnStoreEstados=false;
                        storeEstados.each(function(record)
                        {
                            if(estadoActual==record.get('key'))
                            {
                                actualEnStoreEstados=true;
                            }
                        });
                        if(!actualEnStoreEstados)
                        {
                            comboEstados.clearValue();
                        }
                        comboEstados.setLoading(false);
                    }
                });
            }
        },
        labelWidth: 250
    });
    
    //4 estado
    comboEstados=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboEstados',
        fieldLabel: 'Estado',
        name:'estado',
        model:'GeEstado',
        store: storeEstados,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250/*,
        listeners:
        {
            change:function(record,value)
            {
                comboCiudad.setLoading(true, true);
                storeCiudades.load({
                    params:
                    {
                        codigoTabla:'2TMUNI',
                        idPadre:value
                    },
                    callback: function(records, operation, success)
                    {
                        var ciudadActual=comboCiudad.getValue();
                        var actualEnStoreCiudades=false;
                        storeCiudades.each(function(record)
                        {
                            if(ciudadActual==record.get('key'))
                            {
                                actualEnStoreCiudades=true;
                            }
                        });
                        if(!actualEnStoreCiudades)
                        {
                            comboCiudad.clearValue();
                        }
                        comboCiudad.setLoading(false);
                    }
                });
            }
        }*/
    });
    
    /*4 ciudad X(
    comboCiudad=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCiudad',
        fieldLabel: 'Ciudad',
        name:'ciudad',
        model:'GeCiudad',
        store: storeCiudades,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione un estado...'
    });*/
    
    //5 deducible
    comboDeducible=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboDeducible',
        name:'deducible',
        fieldLabel: 'Deducible',
        store: storeDeducible,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //6 copago
    comboCopago=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCopago',
        name:'copago',
        fieldLabel: 'Copago',
        store: storeCopagos,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //7 suma asegurada
    comboSumaAsegurada=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboSumaAsegurada',
        name:'sumaSegurada',
        fieldLabel: 'Suma asegurada',
        store: storeSumasAseguradas,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //8 circulo hospitalario
    comboCirculoHospitalario=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCirculoHospitalario',
        name:'circuloHospitalario',
        fieldLabel: 'C&iacute;rculo hospitalario',
        store: storeCirculoHospitalario,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...'
    });
    
    //9 cobertura vacunas
    comboCoberturaVacunas=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCoberturaVacunas',
        name:'coberturaVacunas',
        fieldLabel: 'Cobertura de vacunas',
        store: storeCoberturaVacunas,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //10 enfermedades adultos
    comboCoberturaPrevencionEnfermedadesAdultos=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCoberturaPrevencionEnfermedadesAdultos',
        name:'coberturaPrevencionEnfermedadesAdultos',
        fieldLabel: 'Cobertura de prevenci&oacute;n de enfermedades en adultos',
        store: storeCoberturaPrevencionEnfermedadesAdultos,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //11 maternidad
    comboMaternidad=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboMaternidad',
        name:'maternidad',
        fieldLabel: 'Maternidad',
        store: storeMaternidad,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //12 suma maternidad
    comboSumaAseguradaMaternidad=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboSumaAseguradaMaternidad',
        name:'sumaAseguradaMaternidad',
        fieldLabel: 'Suma asegurada maternidad',
        store: storeSumaAseguradaMaternidad,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //13 tabulador reembolso
    comboBaseTabuladorReembolso=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboBaseTabuladorReembolso',
        name:'baseTabuladorReembolso',
        fieldLabel: 'Base de tabulador de reembolso',
        store: storeBaseTabuladorReembolso,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //14 emergencia extranjero
    comboCostoEmergenciaExtranjero=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCostoEmergenciaExtranjero',
        name:'costoEmergenciaExtranjero',
        fieldLabel: 'Costo de emergencia en el extranjero',
        store: storeCostoEmergenciaExtranjero,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //15 combo cobertura eliminacion penalizacion cambio zona
    comboCobElimPenCambioZona=Ext.create('Ext.form.ComboBox2',
    {
        id:'comboCobEiPenCamZona',
        name:'coberturaEliminacionPenalizacionCambioZona',
        fieldLabel: 'Cobertura de elim. de pen. de cambio de zona',
        store: storeCobElimPenCambioZona,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        labelWidth:250
    });
    
    //16 rol (GRID)
    comboRoles=Ext.create('Ext.form.ComboBox',
    {
        id:'comboRoles',
        store: storeRoles,
        queryMode:'local',
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false
    });
    ///////////////////////////////////////////////////////////////////
    ////// Fin de combos de formulario y combos editores de grid //////
    ///////////////////////////////////////////////////////////////////
    
    /////////////////////
    ////// Botones //////
    /*/////////////////*/
    botonVerCoberturas=Ext.create('Ext.Button', {
        text: 'Ver coberturas',
        disabled:true,
        handler: function()
        {
            storeCoberturas.load({
                params:
                {
                    jsonCober_unieco:selected_record.get('cdUnieco'),
                    jsonCober_estado:selected_record.get('estado'),
                    jsonCober_nmpoiza:selected_record.get('nmPoliza'),
                    jsonCober_cdplan:selected_cd_plan,
                    jsonCober_cdramo:selected_record.get('cdRamo'),
                    jsonCober_cdcia:selected_record.get('cdCiaaseg'),
                    jsonCober_situa:selected_nm_plan
                }
            });
            gridCoberturas.setTitle('Plan '+selected_ds_plan);
            botonVerDetalleCobertura.setDisabled(true);
            windowCoberturas.show();
        }
    });
    botonVerDetalleCobertura=Ext.create('Ext.Button', {
        text: 'Ver detalle de coberturas',
        disabled:true,
        handler: detallesCobertura
    });
    botonComprar=Ext.create('Ext.Button', {
        text: 'Comprar',
        disabled:true,
        handler: function()
        {
            
        }
    });
    /*/////////////////*/
    ////// Botones //////
    /////////////////////
    
    ///////////////////////////////
    ////// window coberturas //////
    /*///////////////////////////*/
    gridCoberturas=Ext.create('Ext.grid.Panel',{
        title:'Sin plan',
        store:storeCoberturas,
        height:300,
        selType: 'cellmodel',
        bbar:new Ext.PagingToolbar({
            pageSize: 15,
            store: storeCoberturas,
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
            beforePageText: 'P&aacute;gina',
            afterPageText: 'de {0}'
        }),
        buttons:[botonVerDetalleCobertura],
        columns:
        [
            {
                dataIndex:'dsGarant',
                text:'Cobertura',
                flex:1
            },
            {
                dataIndex:'sumaAsegurada',
                text:'Suma asegurada',
                flex:1
            },
            {
                dataIndex:'deducible',
                text:'Deducible',
                flex:1
            }/*,
            {
                dataIndex:'cdGarant',
                hidden:true
            },
            {
                dataIndex:'cdCiaaseg',
                hidden:true
            },
            {
                dataIndex:'cdRamo',
                hidden:true
            }*/
        ],
        listeners:
        {
            itemclick: function(dv, record, item, index, e)
            {
                var y=this.getSelectionModel().getCurrentPosition().row;
                var x=this.getSelectionModel().getCurrentPosition().column;
                if(x==0)
                {
                    selected_cobertura=record;
                    botonVerDetalleCobertura.setDisabled(false);
                }
                else
                {
                    botonVerDetalleCobertura.setDisabled(true);
                }
            }
        }
    });
    
    coberturasFormPanel =  new Ext.form.FormPanel(
    {
        id:'coberturasFormPanel',
        url:'flujocotizacion/obtenerAyudaCobertura.action',
        border:false,
        layout:'form',
        items:gridCoberturas
    });
    
    windowCoberturas = new Ext.Window(
    {
        plain:true,
	id:'windowCoberturas',
	width: 500,
	height:400,
        modal:true,
	autoScroll:true, 
	title: 'Coberturas',
	layout: 'fit',
	bodyStyle:'padding:5px;',
	buttonAlign:'center',
	closeAction:'hide',
	closable : true,
	items: coberturasFormPanel,
	buttons: [{
		text:'Regresar',
		handler: function() { 
			windowCoberturas.hide(); 
		}
	}]
    });
    /*///////////////////////////*/
    ////// window coberturas //////
    ///////////////////////////////
    
    /////////////////////////////////////
    ////// window ayuda coberturas //////
    /*/////////////////////////////////*/
    windowAyudaCobertura = new Ext.Window({
        title: 'Ayuda Coberturas',
        width: 450,
        height: 350,
        bodyStyle: 'background:white',
        overflow: 'auto',
        modal:true,
        autoScroll: true,
        buttonAlign: 'center',
        closable: false,
        buttons: [/*{
                text: 'Imprimir',
                handler: function() {
                    // *** Cambiamos los nombres de los id's para que al IMPRIMIR se muestre solo lo que queremos
                    if (!Ext.isEmpty(document.getElementById("impresionAyudaInvisible"))) {
                        document.getElementById("impresionAyudaInvisible").id = "impresionAyuda";
                    }
                    if (!Ext.isEmpty(document.getElementById("impresionResultadosCotizacion"))) {
                        document.getElementById("impresionResultadosCotizacion").id = "impresionResultadosCotizacionInvisible";
                    }

                    document.getElementById("impresionAyuda").innerHTML = '<table width=430 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:12px;"><b>Ayuda Coberturas</b></td></tr><tr><td style="font-size:12px; ">' + ayudaVoL + '</td></tr></table>';

                    var spans = document.getElementById("impresionAyuda").getElementsByTagName("span");
                    for (var i = 0; i < spans.length; i++) {
                        spans[i].style.fontSize = "";
                        spans[i].style.lineHeight = "";
                        spans[i].style.textAlign = "";
                    }
                    window.print();


                    // *** Esconder elemento que ya se imprimió para que no se muestre en la pantalla de cotizacion
                    esconderElemento("impresionAyuda");
                }
            }, */{
                text: 'Cerrar',
                handler: function() {
                    windowAyudaCobertura.hide();
                }
            }]
    });
    /*/////////////////////////////////*/
    ////// window ayuda coberturas //////
    /////////////////////////////////////
    
    /////////////////////////////
    ////// grid resultados //////
    /*/////////////////////////*/
    Ext.define('GridResultados',
    {
        extend: 'Ext.grid.Panel',
        /*xtype: 'grouped-grid',
        requires: [
            'Ext.grid.feature.Grouping'
        ],
        collapsible: true,
        features: [{
            ftype: 'grouping',
            groupHeaderTpl: 'Aseguradora: {name}',
            hideGroupedHeader: true,
            startCollapsed: true,
            id: 'restaurantGrouping'
        }],*/
        renderTo:'divResultados',
        hidden:true,
        frame:true,
        store:storeResultados,
        height: 250,
        width: 800,
        title: 'Resultados',
        selType: 'cellmodel',
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: storeResultados,
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
            beforePageText: 'P&aacute;gina',
            afterPageText: 'de {0}'
        }),
        buttonAlign:'center',
        buttons:
        [
            botonComprar,
            botonVerCoberturas,
            {
                text:'Editar cotizaci&oacute;n',
                handler:function()
                {
                    bloquearFormulario(false);
                    gridResultados.hide();
                    window.parent.scrollTo(0,0);
                    botonVerCoberturas.setDisabled(true);
                    //botonComprar.setDisabled(true);
                }
            },
            {
                text:'Nueva cotizaci&oacute;n',
                handler:function()
                {
                    bloquearFormulario(false);
                    formPanel.getForm().reset();
                    gridResultados.hide();
                    window.parent.scrollTo(0,0);
                    storeIncisos.removeAll();
                    storeIncisos.sync();
                    botonVerCoberturas.setDisabled(true);
                    //botonComprar.setDisabled(true);
                }
            },
            {
                text:'Imprimir',
                disabled:true,
                handler:function()
                {
                    
                }
            }
        ],
        listeners:
        {
            itemclick: function(dv, record, item, index, e)
            {
                var y=this.getSelectionModel().getCurrentPosition().row;
                var x=this.getSelectionModel().getCurrentPosition().column;
                if(x>0)
                {
                    var pos='';
                    if(x==1)
                    {
                        pos='Bajo';
                    }
                    else if(x==2)
                    {
                        pos='Medio';
                    }
                    else
                    {
                        pos='Alto';
                    }
                    selected_prima=record.get(pos);
                    selected_cd_plan=record.get('CD'+pos);
                    selected_ds_plan=record.get('DS'+pos);
                    selected_nm_plan=record.get('NM'+pos);
                    selected_record=record;
                    //window.console&&console.log(selected_prima,selected_cd_plan,selected_ds_plan,selected_nm_plan,selected_record);
                    botonVerCoberturas.setDisabled(false);
                    //botonComprar.setDisabled(false);
                }
                else
                {
                    botonVerCoberturas.setDisabled(true);
                    //botonComprar.setDisabled(true);
                }
                //alert("idplan = " + idplan + " desplan = " + desplan+ "  nmplan="+ nmplan + "  mnPrima=" + mnPrima);
                
            }
        },
        columns:
        [
            {   
                header: "cdIdentifica",
                dataIndex:'cdIdentifica',
                sortable:true,
                id:'cdIdentifica',
                hidden:true
            },
            {
                header: "cdUnieco",
                dataIndex:'cdUnieco',
                sortable:true,
                hidden:true			            
            },
            {
                header: "cdCiaaseg",
                dataIndex:'cdCiaaseg',
                sortable:true,
                hidden:true
            },
            {
                header: "dsUnieco",
                dataIndex:'dsUnieco',
                sortable:true,
                hidden:true
            },
            {
                header: "cdPerpag",
                dataIndex:'cdPerpag',
                sortable:true,
                hidden:true
            },
            {
                header: "numeroSituacion",
                dataIndex:'numeroSituacion',
                sortable:true,
                hidden:true
            },
            {
                header: "cdRamo",
                dataIndex:'cdRamo',
                sortable:true,
                hidden:true
            },
            {			          
                header: "Descripci&oacute;n",
                dataIndex:'dsPerpag',
                sortable:false,
                menuDisabled:true,
                flex:1
            },
            {
                header: "cdPlan",
                dataIndex:'cdPlan',
                sortable:true,
                hidden:true
            },
            {			          
                header: "dsPlan",
                dataIndex:'dsPlan',
                sortable:false,
                hidden:true
            },
            {
                header: "feEmisio",
                dataIndex:'feEmisio',
                sortable:false,
                hidden:true
            },
            {			          
                header: "feVencim",
                dataIndex:'feVencim',
                sortable:false,
                hidden:true
            },
            /*generadas*/
            {
                dataIndex: "Bajo",
                header: "Bajo",
                hidden: false,
                id: "Bajo",
                sortable: false,
                flex:1,
                renderer:Ext.util.Format.usMoney
            },
            {
                dataIndex: "CDBajo",
                header: "CDBajo",
                hidden: true,
                id: "CDBajo",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "DSBajo",
                header: "DSBajo",
                hidden: true,
                id: "DSBajo",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "NMBajo",
                header: "NMBajo",
                hidden: true,
                id: "NMBajo",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "Medio",
                header: "Medio",
                hidden: false,
                id: "Medio",
                sortable: false,
                flex:1,
                renderer:Ext.util.Format.usMoney
            },
            {
                dataIndex: "CDMedio",
                header: "CDMedio",
                hidden: true,
                id: "CDMedio",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "DSMedio",
                header: "DSMedio",
                hidden: true,
                id: "DSMedio",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "NMMedio",
                header: "NMMedio",
                hidden: true,
                id: "NMMedio",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "Alto",
                header: "Alto",
                hidden: false,
                id: "Alto",
                sortable: false,
                flex:1,
                renderer:Ext.util.Format.usMoney
            },
            {
                dataIndex: "CDAlto",
                header: "CDAlto",
                hidden: true,
                id: "CDAlto",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "DSAlto",
                header: "DSAlto",
                hidden: true,
                id: "DSAlto",
                sortable: false,
                width: 100
            },
            {
                dataIndex: "NMAlto",
                header: "NMAlto",
                hidden: true,
                id: "NMAlto",
                sortable: false,
                width: 100
            }
        ]
    });
    gridResultados=new GridResultados();
    /*/////////////////////////*/
    ////// grid resultados //////
    /////////////////////////////
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////// Inicio de declaracion de grid                                                                             //////
    ////// http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing //////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Ext.define('EditorIncisos', {
        extend: 'Ext.grid.Panel',

        requires: [
            'Ext.selection.CellModel',
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.util.*',
            'Ext.form.*'
        ],
        xtype: 'cell-editing',

        title: 'Asegurados',
        frame: false,

        initComponent: function() {
            this.cellEditing = new Ext.grid.plugin.CellEditing({
                clicksToEdit: 1
            });

            Ext.apply(this, {
                width: 750,
                height: 200,
                plugins: [this.cellEditing],
                store: storeIncisos,
                columns: 
                [
                    {
                        header: 'Rol',
                        dataIndex: 'rol',
                        flex:1,
                        editor: comboRoles,
                        renderer:function(v)
                        {
                            var leyenda='';
                            if(typeof v == 'string')
                            //tengo solo el indice
                            {
                                //window.console&&console.log('string:');
                                storeRoles.each(function(rec){
                                    //window.console&&console.log('iterando...');
                                    if(rec.data.key==v)
                                    {
                                        leyenda=rec.data.value;
                                    }
                                });
                                //window.console&&console.log(leyenda);
                            }
                            else
                            //tengo objeto que puede venir como Generic u otro mas complejo
                            {
                                //window.console&&console.log('object:');
                                if(v.key&&v.value)
                                //objeto Generic
                                {
                                    leyenda=v.value;
                                }
                                else
                                {
                                    leyenda=v.data.value;
                                }
                                //window.console&&console.log(leyenda);
                            }
                            return leyenda;
                        }
                    },
                    {
                        header: 'Fecha de nacimiento',
                        dataIndex: 'fechaNacimiento',
                        flex:2,
                        renderer: Ext.util.Format.dateRenderer('d M Y'),
                        editor: {
                            xtype: 'datefield',
                            format: 'd/m/Y',
                            editable:true
                        }
                    },
                    {
                        header: 'Sexo',
                        dataIndex: 'sexo',
                        flex:1,
                        editor: comboGeneros,
                        renderer:function(v)
                        {
                            var leyenda='';
                            if(typeof v == 'string')
                            //tengo solo el indice
                            {
                                //window.console&&console.log('string:');
                                storeGeneros.each(function(rec){
                                    //window.console&&console.log('iterando...');
                                    if(rec.data.key==v)
                                    {
                                        leyenda=rec.data.value;
                                    }
                                });
                                //window.console&&console.log(leyenda);
                            }
                            else
                            //tengo objeto que puede venir como Generic u otro mas complejo
                            {
                                //window.console&&console.log('object:');
                                if(v.key&&v.value)
                                //objeto Generic
                                {
                                    leyenda=v.value;
                                }
                                else
                                {
                                    leyenda=v.data.value;
                                }
                                //window.console&&console.log(leyenda);
                            }
                            return leyenda;
                        }
                    },
                    {
                        header: 'Nombre',
                        dataIndex: 'nombre',
                        flex: 1,
                        editor: {
                            //allowBlank: false
                        }
                    },
                    {
                        header: 'Segundo nombre',
                        dataIndex: 'segundoNombre',
                        flex: 2,
                        editor: {
                            //allowBlank: false
                        }
                    },
                    {
                        header: 'Apellido paterno',
                        dataIndex: 'apellidoPaterno',
                        flex: 2,
                        editor: {
                            //allowBlank: false
                        }
                    },
                    {
                        header: 'Apellido materno',
                        dataIndex: 'apellidoMaterno',
                        flex: 2,
                        editor: {
                            //allowBlank: false
                        }
                    },
                    {
                        xtype: 'actioncolumn',
                        width: 30,
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            icon:'resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
                            //iconCls: 'icon-delete',
                            tooltip: 'Quitar inciso',
                            scope: this,
                            handler: this.onRemoveClick
                        }]
                    }
                ],
                selModel: {
                    selType: 'cellmodel'
                },
                tbar: [{
                    icon:'resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
                    text: 'Agregar inciso',
                    scope: this,
                    handler: this.onAddClick
                }],
                /*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/
                //valida las celdas y les pone el estilo rojito
                listeners:
                {
                    // add the validation after render so that validation is not triggered when the record is loaded.
                    afterrender: function (grid)
                    {
                        var view = grid.getView();
                        // validation on record level through "itemupdate" event
                        view.on('itemupdate', function (record, y, node, options)
                            {
                                this.validateRow(this.getColumnIndexes(),record, y, true);
                            },
                            grid
                        );
                    }
                }/*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/

            });

            this.callParent();

            /*this.on('afterlayout', this.loadStore, this, {
                delay: 1,
                single: true
            })*/
        },

        /*loadStore: function() {
            this.getStore().load({
                // store loading is asynchronous, use a load listener or callback to handle results
                callback: this.onStoreLoad
            });
        },

        onStoreLoad: function(){
            Ext.Msg.show({
                title: 'Store Load Callback',
                msg: 'store was loaded, data available for processing',
                icon: Ext.Msg.INFO,
                buttons: Ext.Msg.OK
            });
        },*/

        /*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/
        //regresa las columnas con editor que tengan allowBlank=false (requeridas)
        getColumnIndexes: function () {
            var me, columnIndexes;
            me = this;
            columnIndexes = [];
            Ext.Array.each(me.columns, function (column)
            {
                // only validate column with editor
                if (Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
                    columnIndexes.push(column.dataIndex);
                } else {
                    columnIndexes.push(undefined);
                }
            });
            return columnIndexes;
        },
        validateRow: function (columnIndexes,record, y)
        //hace que una celda de columna con allowblank=false tenga el estilo rojito
        {
            var view = this.getView();
            Ext.each(columnIndexes, function (columnIndex, x)
            {
                if(columnIndex)
                {
                    var cell=view.getCellByPosition({row: y, column: x});
                    cellValue=record.get(columnIndex);
                    if((!cellValue)||(cellValue.lenght==0))
                    {
                        cell.addCls("custom-x-form-invalid-field");
                    }
                }
            });
            return false;
        }/*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/,

        onAddClick: function(){
            // Create a model instance
            var rec = new IncisoSalud({
                rol: new Generic({key:storeRoles.getAt(0).data.key,value:storeRoles.getAt(0).data.value}),
                fechaNacimiento: new Date(),
                sexo: new Generic({key:storeGeneros.getAt(0).data.key,value:storeGeneros.getAt(0).data.value}),
                nombre: '',
                segundoNombre: '',
                apellidoPaterno: '',
                apellidoMaterno: ''
            });

            this.getStore().insert(0, rec);
            
            //para acomodarse en la primer celda para editar
            this.cellEditing.startEditByPosition({
                row: 0, 
                column: 0
            });
        },

        onRemoveClick: function(grid, rowIndex){
            this.getStore().removeAt(rowIndex);
        }
    });
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////// Fin de declaracion de grid                                                                                //////
    ////// http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing //////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    gridIncisos=new EditorIncisos();
    
    ///////////////////////////////
    ////// Inicio formulario //////
    ///////////////////////////////
    formPanel=Ext.create('Ext.form.Panel',
    {
        title:'Cotizaci&oacute;n de salud vital',
        overflowY:'auto',
        overflowX:'auto',
        bodyPadding: 10,
        frame:true,
        width:800,
        buttonAlign:'center',
        renderTo:'maindiv',
        model:'CotizacionSalud',
        items:
        [
            {//0
                id: 'idCotizacion',
                name: 'id',
                xtype: 'numberfield',
                fieldLabel: 'N&uacute;mero de cotizaci&oacute;n',
                readOnly: true,
                labelWidth: 250
            },
            {
                xtype: 'panel',
                layout: 'hbox',
                bodyPadding:5,
                style:'margin-bottom:5px;',
                items: [
                    {
                        id: 'fechaInicioVigencia',
                        name: 'fechaInicioVigencia',
                        fieldLabel: 'Fecha de inicio de vigencia',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        allowBlank:false,
                        labelWidth:180,
                        value:new Date(),
                        listeners:{
                            change:function(field,value)
                            {
                                try
                                {
                                    Ext.getCmp('fechaFinVigencia').setValue(Ext.Date.add(value, Ext.Date.YEAR, 1));
                                }catch(e){}
                            }
                        }
                    },
                    {
                        id: 'fechaFinVigencia',
                        name: 'fechaFinVigencia',
                        fieldLabel: 'Fecha de fin de vigencia',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        readOnly: true,
                        allowBlank:false,
                        labelWidth:180,
                        style:'margin-left:5px;',
                        value:Ext.Date.add(new Date(), Ext.Date.YEAR, 1)
                    }
                ]
            },
            //sexo (inciso)                                   1
            //fecha nacimiento (inciso)                       2
            campoCodigoPostal,                              //3
            comboEstados,                                   //4
            //comboCiudad,                                    4 X(
            comboDeducible,                                 //5
            comboCopago,                                    //6
            comboSumaAsegurada,                             //7
            //comboCirculoHospitalario,                       //8
            comboCoberturaVacunas,                          //9
            comboCoberturaPrevencionEnfermedadesAdultos,    //10
            comboMaternidad,                                //11
            comboSumaAseguradaMaternidad,                   //12
            comboBaseTabuladorReembolso,                    //13
            comboCostoEmergenciaExtranjero,                 //14
            comboCobElimPenCambioZona,                      //15
            //rol (inciso)                                    16
            gridIncisos
        ],
        buttons: [{
            id:'botonCotizar',
            text: 'Cotizar',
            handler: function() {
                // The getForm() method returns the Ext.form.Basic instance:
                var form = this.up('form').getForm();
                if (form.isValid()) {
                    var incisosRecords = storeIncisos.getRange();
                    if(incisosRecords&&incisosRecords.length>0)
                    {
                        var incisosJson = [];
                        storeIncisos.each(function(record,index){
                            incisosJson.push({
                                id: record.get('id'),
                                rol:
                                {
                                    key:typeof record.get('rol')=='string'?record.get('rol'):record.get('rol').get('key'),
                                    value:''
                                },
                                fechaNacimiento: record.get('fechaNacimiento'),
                                sexo:
                                {
                                    key:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                                    value:''
                                },
                                nombre: record.get('nombre'),
                                segundoNombre: record.get('segundoNombre'),
                                apellidoPaterno: record.get('apellidoPaterno'),
                                apellidoMaterno: record.get('apellidoMaterno')
                            });
                        });
                        /*for (var i in incisosRecords) {
                            window.console&&console.log(i);
                            incisosJson.push({
                                id: incisosRecords[i].id,
                                rol:
                                {
                                    key:typeof incisosRecords[i].rol=='string'?incisosRecords[i].rol:incisosRecords[i].rol.key,
                                    value:''
                                },
                                fechaNacimiento: incisosRecords[i].fechaNacimiento,
                                sexo:
                                {
                                    key:typeof incisosRecords[i].sexo=='string'?incisosRecords[i].sexo:incisosRecords[i].sexo.key,
                                    value:''
                                },
                                nombre: incisosRecords[i].nombre,
                                segundoNombre: incisosRecords[i].segundoNombre,
                                apellidoPaterno: incisosRecords[i].apellidoPaterno,
                                apellidoMaterno: incisosRecords[i].apellidoMaterno
                            });
                        }*/
                        var submitValues=form.getValues();
                        submitValues['incisos']=incisosJson;
                        //window.console&&console.log(submitValues);
                        // Submit the Ajax request and handle the response
                        formPanel.setLoading(true);
                        /*Ext.MessageBox.show({
                            msg: 'Cotizando...',
                            width:300,
                            wait:true,
                            waitConfig:{interval:100}
                        });*/
                        Ext.Ajax.request(
                        {
                            url: _URL_COTIZAR,
                            jsonData:Ext.encode(submitValues),
                            success:function(response,opts)
                            {
                                //Ext.MessageBox.hide();
                                formPanel.setLoading(false);
                                var jsonResp = Ext.decode(response.responseText);
                                //window.console&&console.log(jsonResp);
                                if(jsonResp.success==true)
                                {
                                    Ext.getCmp('idCotizacion').setValue(jsonResp.id);
                                    mostrarGrid();
                                }
                            },
                            failure:function(response,opts)
                            {
                                //Ext.MessageBox.hide();
                                formPanel.setLoading(false);
                                //window.console&&console.log("error");
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
                            title:'Datos incompletos',
                            msg: 'Favor de introducir al menos un asegurado',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.ERROR
                        });
                    }
                }
                else
                {
                    Ext.Msg.show({
                        title:'Datos incompletos',
                        msg: 'Favor de introducir todos los campos requeridos',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            }
        },
        {
            text:'Limpiar',
            id:'botonLimpiar',
            handler:function()
            {
                formPanel.getForm().reset();
                storeIncisos.removeAll();
                storeIncisos.sync();
            }
        }
    ]
    });
    ////////////////////////////
    ////// Fin formulario //////
    ////////////////////////////
    
    ///////////////////////////////////////////////
    ////// Cargador de formulario (sin grid) //////
    ///////////////////////////////////////////////
    /*
    Ext.define('LoaderCotizacion',
    {
        extend:'CotizacionSalud',
        proxy:
        {
            type:'ajax',
            url:_URL_CARGAR_COTIZACION,
            reader:{
                type:'json',
                root:'cotizacion'
            }
        }
    });
    
    var loaderCotizacion=Ext.ModelManager.getModel('LoaderCotizacion');
    loaderCotizacion.load(123, {
        success: function(resp) {
            formPanel.getForm().loadRecord(resp);
            bloquearFormulario(true);
            bloquearFormulario(false);
        }
    });
    */
    //////////////////////////////////////////////////////
    ////// Fin de cargador de formulario (sin grid) //////
    //////////////////////////////////////////////////////
    
});