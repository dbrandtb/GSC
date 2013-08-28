///////////////////////////////////////
////// ini catalogos de tatrisit //////
///////////////////////////////////////
var comboGeneros,storeGeneros;                                      //1
//fecha nacimiento                                                    2
var comboEstados,storeEstados;                                      //3
var comboCiudad,storeCiudades;                                      //4
//deducible                                                           5
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
    
    //3 estados
    storeEstados = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
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
        },
        sorters:
        [
            {
                property : 'value',
                direction: 'ASC'
            }
        ]
    });
    
    //4 ciudades
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
        },
        sorters:
        [
            {
                property : 'value',
                direction: 'ASC'
            }
        ]
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
    
    //3 estado
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
        }
    });
    
    //4 ciudad
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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
        emptyText:'Seleccione...'
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

        title: 'Incisos',
        frame: false,

        initComponent: function() {
            this.cellEditing = new Ext.grid.plugin.CellEditing({
                clicksToEdit: 1
            });

            Ext.apply(this, {
                width: 700,
                height: 200,
                plugins: [this.cellEditing],
                store: storeIncisos,
                columns: 
                [
                    {
                        header: 'Rol',
                        dataIndex: 'rol',
                        width: 110,
                        editor: comboRoles,
                        renderer:function(v)
                        {
                            var leyenda='';
                            if(typeof v == 'string')
                            //tengo solo el indice
                            {
                                window.console&&console.log('string:');
                                storeRoles.each(function(rec){
                                    window.console&&console.log('iterando...');
                                    if(rec.data.key==v)
                                    {
                                        leyenda=rec.data.value;
                                    }
                                });
                                window.console&&console.log(leyenda);
                            }
                            else
                            //tengo objeto que puede venir como Generic u otro mas complejo
                            {
                                window.console&&console.log('object:');
                                if(v.key&&v.value)
                                //objeto Generic
                                {
                                    leyenda=v.value;
                                }
                                else
                                {
                                    leyenda=v.data.value;
                                }
                                window.console&&console.log(leyenda);
                            }
                            return leyenda;
                        }
                    },
                    {
                        header: 'Fecha de nacimiento',
                        dataIndex: 'fechaNacimiento',
                        width: 120,
                        renderer: Ext.util.Format.dateRenderer('d M Y'),
                        editor: {
                            xtype: 'datefield',
                            format: 'd/m/y',
                            editable:false
                        }
                    },
                    {
                        header: 'Sexo',
                        dataIndex: 'sexo',
                        width: 70,
                        editor: comboGeneros,
                        renderer:function(v)
                        {
                            var leyenda='';
                            if(typeof v == 'string')
                            //tengo solo el indice
                            {
                                window.console&&console.log('string:');
                                storeGeneros.each(function(rec){
                                    window.console&&console.log('iterando...');
                                    if(rec.data.key==v)
                                    {
                                        leyenda=rec.data.value;
                                    }
                                });
                                window.console&&console.log(leyenda);
                            }
                            else
                            //tengo objeto que puede venir como Generic u otro mas complejo
                            {
                                window.console&&console.log('object:');
                                if(v.key&&v.value)
                                //objeto Generic
                                {
                                    leyenda=v.value;
                                }
                                else
                                {
                                    leyenda=v.data.value;
                                }
                                window.console&&console.log(leyenda);
                            }
                            return leyenda;
                        }
                    },
                    {
                        header: 'Nombre',
                        dataIndex: 'nombre',
                        flex: 2,
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
                }]
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
    
    ///////////////////////////////
    ////// Inicio formulario //////
    ///////////////////////////////
    formPanel=Ext.create('Ext.form.Panel',
    {
        title:'Asegurados',
        overflowY:'auto',
        overflowX:'auto',
        bodyPadding: 10,
        width:800,
        //height:500,
        renderTo:'maindiv',
        model:'CotizacionSalud',
        items:
        [
            {                                               //0
                id: 'idCotizacion',
                name:'id',
                xtype: 'numberfield',
                fieldLabel: 'N&uacute;mero de cotizaci&oacute;n',
                readOnly: true
            },
            //sexo (inciso)                                   1
            //fecha nacimiento (inciso)                       2
            comboEstados,                                   //3
            comboCiudad,                                    //4
            {                                               //5
                id: 'deducible',
                name:'deducible',
                xtype: 'numberfield',
                fieldLabel: 'Deducible',
                allowBlank: false,
                renderer:Ext.util.Format.usMoney
            },
            comboCopago,                                    //6
            comboSumaAsegurada,                             //7
            comboCirculoHospitalario,                       //8
            comboCoberturaVacunas,                          //9
            comboCoberturaPrevencionEnfermedadesAdultos,    //10
            comboMaternidad,                                //11
            comboSumaAseguradaMaternidad,                   //12
            comboBaseTabuladorReembolso,                    //13
            comboCostoEmergenciaExtranjero,                 //14
            comboCobElimPenCambioZona,                      //15
            //rol (inciso)                                    16
            new EditorIncisos()
        ],
        buttons: [{
            text: 'Cotizar',
            handler: function() {
                // The getForm() method returns the Ext.form.Basic instance:
                var form = this.up('form').getForm();
                if (form.isValid()) {
                    var incisosRecords = storeIncisos.getRange();
                    if(incisosRecords&&incisosRecords.length>0)
                    {
                        var incisosJson = [];
                        for (var i in incisosRecords) {
                            incisosJson.push({
                                id: incisosRecords[i].get('id'),
                                rol:
                                {
                                    key:typeof incisosRecords[i].get('rol')=='string'?incisosRecords[i].get('rol'):incisosRecords[i].get('rol').get('key'),
                                    value:''
                                },
                                fechaNacimiento: incisosRecords[i].get('fechaNacimiento'),
                                sexo:
                                {
                                    key:typeof incisosRecords[i].get('sexo')=='string'?incisosRecords[i].get('sexo'):incisosRecords[i].get('sexo').get('key'),
                                    value:''
                                },
                                nombre: incisosRecords[i].get('nombre'),
                                segundoNombre: incisosRecords[i].get('segundoNombre'),
                                apellidoPaterno: incisosRecords[i].get('apellidoPaterno'),
                                apellidoMaterno: incisosRecords[i].get('apellidoMaterno')
                            });
                        }
                        var submitValues=form.getValues();
                        submitValues['incisos']=incisosJson;
                        window.console&&console.log(submitValues);
                        // Submit the Ajax request and handle the response
                        Ext.MessageBox.show({
                            msg: 'Cotizando...',
                            width:300,
                            wait:true,
                            waitConfig:{interval:100}
                        });
                        Ext.Ajax.request(
                        {
                            url: _URL_COTIZAR,
                            jsonData:Ext.encode(submitValues),
                            success:function(response,opts)
                            {
                                Ext.MessageBox.hide();
                                var jsonResp = Ext.decode(response.responseText);
                                window.console&&console.log(jsonResp);
                                if(jsonResp.success==true)
                                {
                                    window.location  = _URL_RESULTADO_COTIZACION;
                                }
                            },
                            failure:function(response,opts)
                            {
                                Ext.MessageBox.hide();
                                window.console&&console.log("error");
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
        }]
    });
    ////////////////////////////
    ////// Fin formulario //////
    ////////////////////////////
    
    ///////////////////////////////////////////////
    ////// Cargador de formulario (sin grid) //////
    ///////////////////////////////////////////////
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
        }
    });
    //////////////////////////////////////////////////////
    ////// Fin de cargador de formulario (sin grid) //////
    //////////////////////////////////////////////////////
    
});