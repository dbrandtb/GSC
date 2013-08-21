var comboCiudad,storeCiudades;
var comboCopago,storeCopagos;
var comboSumaAsegurada,storeSumasAseguradas;
var comboCirculoHospitalario,storeCirculoHospitalario;
var comboCoberturaVacunas,storeCoberturaVacunas;
var comboCoberturaPrevencionEnfermedadesAdultos,storeCoberturaPrevencionEnfermedadesAdultos;
var comboMaternidad,storeMaternidad;
var comboSumaAseguradaMaternidad,storeSumaAseguradaMaternidad;
var comboBaseTabuladorReembolso,storeBaseTabuladorReembolso;
var comboCostoEmergenciaExtranjero,storeCostoEmergenciaExtranjero;
var comboGeneros,storeGeneros;
var comboRoles,storeRoles;

//variables globales
var formPanel;
var cotizacion;
var mapaIncisos;


var Cotizacion=function()
{
    this.ciudad=0;
    this.deducible=0;
    this.copago=0;
    this.sumaAsegurada=0;
    this.circuloHospitalario=0;
    this.coberturaVacunas=0;
    this.coberturaPrevencionEnfermedadesAdultos=0;
    this.maternidad=0;
    this.sumaAseguradaMaternidad=0;
    this.baseTabuladorRembolso=0;
    this.costoEmergenciaExtranjero=0;
    this.incisos=[];
};

function dump()
{
    window.console&&console.log(cotizacion);
}

Ext.onReady(function(){
    
    Ext.define('Inciso', {
        extend   : 'Ext.data.Model',
        fields   : [
            {name:'rol',                type: 'Generic'},
            {name:'fechaNacimiento',    type: 'date'},
            {name:'genero',             type: 'Generic'},
            {name:'nombre',             type: 'string'},
            {name:'segundoNombre',      type: 'string'},
            {name:'apellidoPaterno',    type: 'string'},
            {name:'apellidoMaterno',    type: 'string'}
        ]
    });
    
    cotizacion=new Cotizacion();
    mapaIncisos = new Ext.util.HashMap();
    
    storeCiudades = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_CIUDADES,
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
    
    storeCopagos = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_COPAGOS,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeSumasAseguradas = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_SUMAS_ASEGURADAS,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeCirculoHospitalario = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_CIRCULOS_HOSPITALARIOS,
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
    
    storeCoberturaVacunas = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_COBERTURA_VACUNAS,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeCoberturaPrevencionEnfermedadesAdultos = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_COBERTURA_PREVENCION_ENFERMEDADES_ADULTOS,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeMaternidad = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_MATERNIDAD,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeSumaAseguradaMaternidad = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_SUMA_ASEGURADA_MATERNIDAD,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeBaseTabuladorReembolso = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_BASE_TABULADOR_REEMBOLSO,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeCostoEmergenciaExtranjero = new Ext.data.Store({
        model: 'Generic',
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_COSTO_EMERGENCIA_EXTRANJERO,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeGeneros = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_GENEROS,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    storeRoles = new Ext.data.Store({
        model: 'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url : _URL_JSON_OBTEN_ROLES,
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
    comboCiudad=Ext.create('Ext.form.ComboBox',
    {
        id:'comboCiudad',
        fieldLabel: 'Ciudad',
        store: storeCiudades,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.ciudad=b;dump();}}
    });
    
    comboCopago=Ext.create('Ext.form.ComboBox',
    {
        id:'comboCopago',
        fieldLabel: 'Copago',
        store: storeCopagos,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.copago=b;dump();}}
    });
    
    comboSumaAsegurada=Ext.create('Ext.form.ComboBox',
    {
        id:'comboSumaAsegurada',
        fieldLabel: 'Suma asegurada',
        store: storeSumasAseguradas,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.sumaAsegurada=b;dump();}}
    });
    
    comboCirculoHospitalario=Ext.create('Ext.form.ComboBox',
    {
        id:'comboCirculoHospitalario',
        fieldLabel: 'C&iacute;rculo hospitalario',
        store: storeCirculoHospitalario,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.circuloHospitalario=b;dump();}}
    });
    
    comboCoberturaVacunas=Ext.create('Ext.form.ComboBox',
    {
        id:'comboCoberturaVacunas',
        fieldLabel: 'Cobertura de vacunas',
        store: storeCoberturaVacunas,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.coberturaVacunas=b;dump();}}
    });
    
    comboCoberturaPrevencionEnfermedadesAdultos=Ext.create('Ext.form.ComboBox',
    {
        id:'comboCoberturaPrevencionEnfermedadesAdultos',
        fieldLabel: 'Cobertura de prevenci&oacute;n de enfermedades en adultos',
        store: storeCoberturaPrevencionEnfermedadesAdultos,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.coberturaPrevencionEnfermedadesAdultos=b;dump();}}
    });
    
    comboMaternidad=Ext.create('Ext.form.ComboBox',
    {
        id:'comboMaternidad',
        fieldLabel: 'Maternidad',
        store: storeMaternidad,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.maternidad=b;dump();}}
    });
    
    comboSumaAseguradaMaternidad=Ext.create('Ext.form.ComboBox',
    {
        id:'comboSumaAseguradaMaternidad',
        fieldLabel: 'Suma asegurada maternidad',
        store: storeSumaAseguradaMaternidad,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.sumaAseguradaMaternidad=b;dump();}}
    });
    
    comboBaseTabuladorReembolso=Ext.create('Ext.form.ComboBox',
    {
        id:'comboBaseTabuladorReembolso',
        fieldLabel: 'Base de tabulador de reembolso',
        store: storeBaseTabuladorReembolso,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.baseTabuladorRembolso=b;dump();}}
    });
    
    comboCostoEmergenciaExtranjero=Ext.create('Ext.form.ComboBox',
    {
        id:'comboCostoEmergenciaExtranjero',
        fieldLabel: 'Costo de emergencia en el extranjero',
        store: storeCostoEmergenciaExtranjero,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false,
        emptyText:'Seleccione...',
        listeners:{change:function(a,b){cotizacion.costoEmergenciaExtranjero=b;dump();}}
    });
    
    comboGeneros=Ext.create('Ext.form.ComboBox',
    {
        id:'comboGeneros',
        store: storeGeneros,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false
    });
    
    comboRoles=Ext.create('Ext.form.ComboBox',
    {
        id:'comboRoles',
        store: storeRoles,
        displayField: 'value',
        valueField: 'key',
        allowBlank:false,
        editable:false
    });
    
    formPanel=Ext.create('Ext.form.Panel',
    {
        title:'Asegurados',
        overflowY:'auto',
        overflowX:'auto',
        bodyPadding: 10,
        width:800,
        height:500,
        url:_URL_ASEGURAR,
        renderTo:'maindiv',
        items:
        [
            comboCiudad,
            {
                id: 'deducible',
                xtype: 'numberfield',
                fieldLabel: 'Deducible',
                allowBlank: false,
                listeners:{change:function(a,b){cotizacion.deducible=b;dump();}}
            },
            comboCopago,
            comboSumaAsegurada,
            comboCirculoHospitalario,
            comboCoberturaVacunas,
            comboCoberturaPrevencionEnfermedadesAdultos,
            comboMaternidad,
            comboSumaAseguradaMaternidad,
            comboBaseTabuladorReembolso,
            comboCostoEmergenciaExtranjero,
            new EditorIncisos()
        ]
    });
    
    /*Ext.create('Ext.Viewport', {
        renderTo: 'maindiv',
        layout:'border',
        defaults: {
            collapsible: true,
            split: true,
            bodyStyle: 'padding:15px'
        },
        items: [{
            title: 'Incisos',
            region: 'south',
            height: 150,
            minSize: 75,
            maxSize: 250,
            cmargins: '5 0 0 0'
        },{
            title: 'Datos generales',
            collapsible: false,
            region:'center',
            margins: '5 0 0 0',
            overflowY:'auto',
            items:formPanel
        }]
    });*/
});

/* http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing */
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
            store: new Ext.data.Store({
                // destroy the store if the grid is destroyed
                autoDestroy: true,
                model: 'Inciso',
                data:
                [
                    {
                        'rol':new Generic({'key':'1','value':'Titular'}),
                        'fechaNacimiento':new Date(),
                        'genero':new Generic({'key':'1','value':'Hombre'}),
                        'nombre':'Alvaro',
                        'segundoNombre':'Jair',
                        'apellidoPaterno':'Martínez',
                        'apellidoMaterno':'Varela'
                    },
                    {
                        'rol':new Generic({'key':'2','value':'Hermano(a)'}),
                        'fechaNacimiento':new Date(),
                        'genero':new Generic({'key':'1','value':'Hombre'}),
                        'nombre':'Ricardo',
                        'segundoNombre':'',
                        'apellidoPaterno':'Bautista',
                        'apellidoMaterno':'Silva'
                    }
                ]
                /*proxy: {
                    type: 'ajax',
                    // load remote data using HTTP
                    url: 'resources/data/grid/plants.xml',
                    // specify a XmlReader (coincides with the XML format of the returned data)
                    reader: {
                        type: 'xml',
                        // records will have a 'plant' tag
                        record: 'plant'
                    }
                }*//*,
                sorters: [{
                    property: 'common',
                    direction:'ASC'
                }]*/
            }),
            columns: 
            [
                {
                    header: 'Rol',
                    dataIndex: 'rol',
                    width: 110,
                    editor: comboRoles,
                    renderer:function(v)
                    {
                        var leyenda='no';
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
                        format: 'd/m/y'
                    }
                },
                {
                    header: 'Sexo',
                    dataIndex: 'genero',
                    width: 70,
                    editor: comboGeneros,
                    renderer:function(v)
                    {
                        var leyenda='no';
                        if(typeof v == 'string')
                        //tengo solo el indice
                        {
                            storeGeneros.each(function(rec){
                                window.console&&console.log(rec);
                                if(rec.data.key==v)
                                {
                                    leyenda=rec.data.value;
                                }
                            });
                        }
                        else
                        //tengo objeto que puede venir como Generic u otro mas complejo
                        {
                            window.console&&console.log(v);
                            if(v.key&&v.value)
                            //objeto Generic
                            {
                                leyenda=v.value;
                            }
                            else
                            {
                                leyenda=v.data.value;
                            }
                        }
                        return leyenda;
                    }
                },
                {
                    header: 'Nombre',
                    dataIndex: 'nombre',
                    flex: 2,
                    editor: {
                        allowBlank: false
                    }
                },
                {
                    header: 'Segundo nombre',
                    dataIndex: 'segundoNombre',
                    flex: 2,
                    editor: {
                        allowBlank: false
                    }
                },
                {
                    header: 'Apellido paterno',
                    dataIndex: 'apellidoPaterno',
                    flex: 2,
                    editor: {
                        allowBlank: false
                    }
                },
                {
                    header: 'Apellido materno',
                    dataIndex: 'apellidoMaterno',
                    flex: 2,
                    editor: {
                        allowBlank: false
                    }
                },
                {
                    xtype: 'actioncolumn',
                    width: 30,
                    sortable: false,
                    menuDisabled: true,
                    items: [{
                        icon:'resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.gif',
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
        var rec = new Inciso({
            rol: new Generic({key:'2',value:'Hermano(a)'}),
            fechaNacimiento: new Date(),
            genero: new Generic({key:'1',value:'Hombre'}),
            nombre: '',
            segundoNombre: '',
            apellidoPaterno: '',
            apellidoMaterno: ''
        });
        
        this.getStore().insert(0, rec);
        /*this.cellEditing.startEditByPosition({
            row: 0, 
            column: 0
        });*/
    },
    
    onRemoveClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    }
})