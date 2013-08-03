var helpMap = new Map();
Ext.onReady(function() {    /////////////// EMPIEZA EL FUNCIONAMIENTO DE EXT
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = "side";
    var store;
    var storeValidate;
    var storeAtributos;
    var url;
    var urlValidate;
    var nmAtributos;
    var desAtributoss = 'a';
    var descripcionReporte;
    var codReporte;
    var idAseguradora = ' ';
    var descAseguradora;
    var descProducto;
    var descCliente;
    var desde;
    var hasta;
    var desdeAux;

    function cargarTabla() {

        url = _ACTION_BUSCAR_REPORTES;       //Mapeo con struts
        store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: url,
                waitMsg: getLabelFromMap('400017', helpMap, 'Espere por favor...')
            }),
            reader: new Ext.data.JsonReader({
                root:'reportes',
                totalProperty: 'totalCount',
                id: 'totales'
            }, [
                {
                    name: 'cdReporte',
                    type: 'string',
                    mapping:'cdReporte'
                },
                {
                    name: 'dsReporte',
                    type: 'string',
                    mapping:'dsReporte'
                },
                {
                    name: 'nmReporte',
                    type: 'string',
                    mapping:'nmReporte'
                }
                //                {name: 'numAtributos',  type: 'string',  mapping:'numAtributos'}


            ]),listeners: {
                load: function(store, response) {
                    if (store.getCount() < 1) {
                        Ext.MessageBox.show({
                            title: 'Advertencia',
                            msg: 'No hay Datos Disponibles',
                            buttons: Ext.MessageBox.OK

                        });
                    }
                },
                loadexception: function(proxy, store, response, e) {

                    Ext.MessageBox.show({
                        title: 'Advertencia',
                        msg: 'No hay Datos Disponibles',
                        buttons: Ext.MessageBox.OK

                    });
                }}
        });


        return store;
    }

    ;

    var nmReporte;
    var tiGrafico;
    var graficoReporte;
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"Por favor Espere..."});

    urlValidate = 'reportesPrincipal/validarGrafico.action';       //Mapeo con struts     //Mapeo con struts
    storeAtributos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: urlValidate
        }),
        reader: new Ext.data.JsonReader({
            root:'reportes',
            totalProperty: 'totalCountAtributos',
            id: 'totalesAtributos'
        }, [
            {
                name:'numAtributos',
                type:'string',
                mapping:'numAtributos'
            }
        ])
        ,listeners: {
            load: function(storeAtributos) {

                myMask.hide();
            }}

    });
    storeValidate = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: urlValidate
        }),
        reader: new Ext.data.JsonReader({
            root:'reportes',
            totalProperty: 'totalCountValidate',
            id: 'totalesValidate'
        }, [
            {
                name: 'cdReporte',
                type: 'string',
                mapping:'cdReporte'
            },
            {
                name:'numAtributos',
                type:'string',
                mapping:'numAtributos'
            }
        ]),listeners: {
            load: function(storeValidate) {
                tiGrafico = nmReporte;
                if (storeValidate.getCount() > 0) {
                    Ext.getCmp('grafico').setDisabled(false);
                }
                myMask.hide();
            }}

    });


    /////combo
    var storeDatos;

    function cargarComboGrafico() {
        storeDatos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_GRAFICO + '?cdReporte=' + codReporte
            }),
            reader: new Ext.data.JsonReader({
                root:'graficoTabla',
                totalProperty: 'totalCountCombo',
                id: 'totalesCombo'
            }, [
                {
                    name: 'nombreGrafico',
                    type: 'string',
                    mapping:'nmGrafico'
                },
                {
                    name:  'descriCombo',
                    type: 'string',
                    mapping:'descripcion'
                },

            ]),
            remoteSort: true
        });
        storeDatos.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });
        return storeDatos;
    }

    /////combo  ASEGURADORA
    function cargarComboAseguradora() {
        storeDatosAsegurador = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_ASEGURADORA
            }),
            reader: new Ext.data.JsonReader({
                root:'combo',
                totalProperty: 'totalCountAseguradora',
                id: 'totalesAseguradora'
            }, [
                {
                    name: 'codigo',
                    type: 'string',
                    mapping:'codigo'
                },
                {
                    name:  'aseguradora',
                    type: 'string',
                    mapping:'descripC'
                },

            ]),
            remoteSort: true
        });
        storeDatosAsegurador.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });

        return storeDatosAsegurador;
    }


    function cargarComboProductos() {
        var urlProductos = _ACTION_COMBO_PRODUCTO;
        storeDatosProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: urlProductos         //Mapeo con struts
            }),
            reader: new Ext.data.JsonReader({
                root:'comboProductos',
                totalProperty: 'totalCountProductos',
                id: 'totalesProductos'
            }, [
                {
                    name: 'codigoProducto',
                    type: 'string',
                    mapping:'codigo'
                },
                {
                    name:  'productos',
                    type: 'string',
                    mapping:'descripC'
                },

            ]),
            remoteSort: true
        });
        storeDatosProductos.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });

        return storeDatosProductos;
    }


    ////////Cuenta///////////////////////////////////////////////////////////////////
    function cargarComboCuenta() {
        storeDatosCuenta = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_CUENTA + '?cdReporte=0&grafico=' + descAseguradora + ''
            }),
            reader: new Ext.data.JsonReader({
                root:'comboCuenta',
                totalProperty: 'totalCountCuenta',
                id: 'totalesCuenta'
            }, [
                {
                    name: 'codigoCuenta',
                    type: 'string',
                    mapping:'codigo'
                },
                {
                    name:  'cuenta',
                    type: 'string',
                    mapping:'descripC'
                },

            ]),
            remoteSort: true
        });
        storeDatosCuenta.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });
        return storeDatosCuenta;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////



    // busquedaForm.render(document.body);


    //// Formulario con 5 Atributos

    var Mensaje = new Ext.form.TextField({
        id:'mensaje',
        fieldLabel: 'Reporte: ',
        labelAlign: 'right',
        name:'dsReporte',
        anchor:'98%',
        width:165,
        disabled : true,
        labelSeparator:''

    });

    var atributo1 = new Ext.form.ComboBox({
        name :'asegurador',
        id: 'descAseguradora',
        fieldLabel: 'Aseguradora',
        labelAlign: 'right',
        allowBlank: false,
        forceSelection: true,
        store:cargarComboAseguradora(),
        displayField:'aseguradora',
        valueField : 'codigo',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        anchor:'98%',

        listeners:{select:{fn:function(combo, value) {
            descAseguradora = storeDatosAsegurador.data.items[combo.selectedIndex].data.aseguradora;
        }}
        }

    });

    var atributo2 = new Ext.form.ComboBox({
        id:'formato1',
        fieldLabel: 'Producto',
        labelAlign: 'right',
        allowBlank: false,
        forceSelection: true,
        store: cargarComboProductos(),
        displayField:'productos',
        valueField : 'codigoProducto',
        mode: 'local',
        anchor:'98%',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        listeners:{select:{fn:function(combo, value) {

            descProducto = storeDatosProductos.data.items[combo.selectedIndex].data.productos;
        }}
        }
    });

    var atributo3 = new Ext.form.ComboBox({
        id:'formato2',
        fieldLabel: 'Cuenta/Cliente',
        labelAlign: 'right',
        store: cargarComboCuenta(),
        allowBlank: false,
        forceSelection: true,
        displayField:'cuenta',
        valueField : 'codigoCuenta',//'cuenta',
        mode: 'local',
        anchor:'98%',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        selectOnFocus:true,
        listeners:{select:{fn:function(combo, value) {

            descCliente = storeDatosCuenta.data.items[combo.selectedIndex].data.cuenta;
        }}
        }});

    var atributo4 = new Ext.form.DateField({
        fieldLabel: 'Fecha Inicio',
        labelAlign: 'right',
        format: 'd-m-Y',
        showWeekNumber: true,
        anchor:'90%',
        allowBlank: false


    });

    var atributo5 = new Ext.form.DateField({
        fieldLabel: 'Fecha Fin',
        labelAlign: 'right',
        format: 'd-m-Y',
        anchor:'90%',
        showWeekNumber: true,
        allowBlank: false


    });


    var ejecutarForm = new Ext.FormPanel({
        id:'ejecutarForm1',
        labelAlign:'right',
        baseCls: 'x-plain',
        url:'reportesGenerador/guardarReporteAux.action',
        anchor:'100%',
        items:[Mensaje,atributo1,atributo2,atributo3,atributo4,atributo5]
    });

    var windowEjecutar = new Ext.Window({
        title: 'Atributos',
        width: 320,
        height:280,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: ejecutarForm,
        buttons: [
            {
                text: 'Aceptar',
                handler: function() {
                    if (ejecutarForm.form.isValid()) {
                        var mensaje = '';

                        if (atributo4.getValue() > atributo5.getValue()) {
                            Ext.MessageBox.alert('Error', ' La Fecha Fin debe ser mayor que la Fecha Inicio');

                        } else {
                            ejecutarForm.form.submit({});
                            window.open('../jaspers/generador.action?dsReporte=' + nmReporte
                                    + '&asegurador=' + atributo1.getValue() + '&producto='
                                    + atributo2.getValue() + '&cliente=' + atributo3.getValue()
                                    + '&fechIni=' + atributo4.getValue().format('d/m/Y')
                                    + '&fechFin=' + atributo5.getValue().format('d/m/Y')
                                    + '&cdReporte=' + codReporte + '&descAsegurador='
                                    + Ext.getCmp('descAseguradora').getValue() + '&descProducto=' + descProducto
                                    + '&descCliente=' + descCliente);
                        }
                        grid2.destroy();
                        createGrid();
                        reloadGrid();


                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                    }


                }
            },
            {
                text: 'Cancelar',
                handler: function() {
                    windowEjecutar.hide();
                    ejecutarForm.getForm().reset();
                    atributo4.setDate('');
                    atributo5.setDate('');
                    /*grid2.destroy();
                     createGrid();
                     reloadGrid();*/


                }
            }
        ]
    });
    ///////////////////////////Formulario de 4 Atrubtos CART001

    var reporteAtributos4 = new Ext.form.TextField({
        id:'reporteAtributos4',
        fieldLabel: 'Reporte: ',
        labelAlign: 'right',
        name:'dsReporte',
        anchor:'98%',
        width:165,
        disabled : true,
        labelSeparator:''

    });

    var atributo4_1 = new Ext.form.ComboBox({
        name :'asegurador',
        fieldLabel: 'Aseguradora',
        labelAlign: 'right',
        allowBlank: false,
        forceSelection: true,
        store:cargarComboAseguradora(),
        displayField:'aseguradora',
        valueField : 'codigo',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        anchor:'98%',
        listeners:{select:{fn:function(combo, value) {
            descAseguradora = storeDatosAsegurador.data.items[combo.selectedIndex].data.aseguradora;
        }}
        }

    });

    var atributo4_2 = new Ext.form.ComboBox({
        id:'producto4_2',
        fieldLabel: 'Producto',
        labelAlign: 'right',
        store: cargarComboProductos(),
        displayField:'productos',
        allowBlank: false,
        forceSelection: true,
        valueField : 'codigoProducto',
        mode: 'local',
        anchor:'98%',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        listeners:{select:{fn:function(combo, value) {

            descProducto = storeDatosProductos.data.items[combo.selectedIndex].data.productos;
        }}
        }
    });

    var atributo4_3 = new Ext.form.DateField({
        fieldLabel: 'Fecha Inicio',
        labelAlign: 'right',
        format: 'd-m-Y',
        showWeekNumber: true,
        anchor:'90%',
        allowBlank: false
    });

    var atributo4_4 = new Ext.form.DateField({
        fieldLabel: 'Fecha Fin',
        labelAlign: 'right',
        format: 'd-m-Y',
        anchor:'90%',
        showWeekNumber: true,
        allowBlank: false
    });


    var ejecutarForm4 = new Ext.FormPanel({
        id:'ejecutarForm4',
        labelAlign:'right',
        baseCls: 'x-plain',
        anchor:'100%',
        items:[reporteAtributos4,atributo4_1,atributo4_2,atributo4_3,atributo4_4]
    });

    var windowEjecutar4 = new Ext.Window({
        title: 'Atributos',
        width: 320,
        height:280,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: ejecutarForm4,
        buttons: [
            {
                text: 'Aceptar',
                handler: function() {
                    var mensaje = '';
                    if (ejecutarForm4.form.isValid()) {


                        if (atributo4_1.getValue() == '' || atributo4_2.getValue() == '' || atributo4_3.getValue() == '' || atributo4_4.getValue() == '') {

                            Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                        } else {
                            if (atributo4_3.getValue() > atributo4_4.getValue()) {
                                Ext.MessageBox.alert('Error', ' El Campo Fecha fin debe ser mayor que el campo Fecha Inicio');

                            } else {
                                window.open('../jaspers/generador.action?dsReporte=' + nmReporte + '&asegurador=' + atributo4_1.getValue() +
                                            '&producto=' + atributo4_2.getValue() + '&cliente=' + atributo3.getValue() + '&fechIni=' + atributo4_3.getValue().format('d/m/Y') + '&fechFin=' + atributo4_4.getValue().format('d/m/Y') +
                                            '&cdReporte=' + codReporte + '&descAsegurador=' + descAseguradora + '&descProducto=' + descProducto + '&descCliente=' + descCliente);
                            }


                        }
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                    }

                }
            },
            {
                text: 'Cancelar',
                handler: function() {
                    windowEjecutar4.hide();
                    ejecutarForm4.getForm().reset();
                    atributo4_3.setDate('');
                    atributo4_4.setDate('');
                    /*grid2.destroy();
                     createGrid();
                     reloadGrid();*/

                }
            }
        ]
    });

    ///////////////////////////Formulario de 2 atributos

    var cdReporte1 = new Ext.form.TextField({
        fieldLabel: '',
        name:'graficoReporte',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden : true
    });

    var Mensaje1 = new Ext.form.TextField({
        id:'mensaje1',
        fieldLabel: 'Reporte: ',
        labelAlign: 'right',
        name:'dsReporte',
        disabled : true,
        width:165,
        anchor:'98%',
        labelSeparator:''

    });

    var atributo11 = new Ext.form.ComboBox({
        id:'formato3',
        name :'asegurador',
        fieldLabel: 'Aseguradora',
        allowBlank: true,
        //        forceSelection: true,
        labelAlign: 'right',
        store: cargarComboAseguradora(),
        displayField:'aseguradora',
        valueField : 'codigo',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        anchor:'98%',
        listeners:{select:{fn:function(combo, value) {
            descAseguradora = storeDatosAsegurador.data.items[combo.selectedIndex].data.aseguradora;
        }}
        }
    });

    var atributo22 = new Ext.form.ComboBox({
        id:'formato4',
        fieldLabel: 'Producto',
        labelAlign: 'right',
        allowBlank: true,
        //        forceSelection: true,
        store: cargarComboProductos(),
        displayField:'productos',
        valueField : 'codigoProducto',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Seleccione un formato...',
        anchor:'98%',
        selectOnFocus:true,
        listeners:{select:{fn:function(combo, value) {
            descProducto = storeDatosProductos.data.items[combo.selectedIndex].data.productos;

        }}
        }
    });


    var ejecutarForm2 = new Ext.FormPanel({
        id:'ejecutarForm2',
        labelAlign:'right',
        baseCls: 'x-plain',
        anchor:'100%',
        items:[Mensaje1,atributo11,atributo22]
    });

    var windowEjecutarDos = new Ext.Window({
        title: 'Atributos',
        width: 320,
        height:220,
        layout: 'fit',
        closable:false,
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: ejecutarForm2,
        buttons: [
            {
                text: 'Aceptar',
                handler: function() {
                    if (ejecutarForm2.form.isValid()) {
                        var mensaje = '';

                        if (atributo11.getValue() == '' || atributo22.getValue() == '') {

                            Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                        } else {

                            window.open('../jaspers/generador.action?dsReporte=' + nmReporte + '&asegurador=' + atributo11.getValue() +
                                        '&producto=' + atributo22.getValue() + '&cliente=0&fechIni=01/01/07&fechFin=31/12/08&cdReporte=' + codReporte +
                                        '&descAsegurador=' + descAseguradora + '&descProducto=' + descProducto);
                        }
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                    }
                }
            },
            {
                text: 'Cancelar',
                handler: function() {
                    windowEjecutarDos.hide();
                    ejecutarForm2.getForm().reset();
                    /*grid2.destroy();
                     createGrid();
                     reloadGrid();*/

                }
            }
        ]
    });

    var nombreGrafico;

    ////////////////////AGregar comboo///////
    var comboGrafico = function() {

        ////////////////////AGregar comboo///////
        var tipoGrafico = new Ext.form.ComboBox({
            forceSelection: true,
            fieldLabel: 'Tipo',
            store: cargarComboGrafico(),
            displayField:'descriCombo',
            allowBlank: false,
            //   valueField: 'nombreGrafico',
            valueField: 'descriCombo',
            mode: 'local',
            anchor:'95%',
            triggerAction: 'all',
            emptyText:'Seleccione un formato...',

            listeners:{select:{fn:function(combo, value) {


                nmReporte = tiGrafico;
                if (tipoGrafico.getValue() == "BARRAS")
                    nmReporte = "G" + nmReporte + "B";
                if (tipoGrafico.getValue() == "CIRCULAR")
                    nmReporte = "G" + nmReporte + "T";


            }}
            }

        });


        var agregarGraficoForm = new Ext.form.FormPanel({
            id:'agregarGraficoForm',
            labelAlign: 'right' ,
            baseCls: 'x-plain',
            items: [tipoGrafico]

        });

        var AgregarGraficonWindow = new Ext.Window({

            title: "Agregar Gr&aacute;fico",
            layout: 'fit',
            plain: true,
            modal: true,
            closable:false,
            width: 350,
            heigth: 400,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            items: agregarGraficoForm,
            buttons: [
                {
                    text: 'Aceptar',
                    handler: function() {
                        if (agregarGraficoForm.form.isValid()) {
                            if (nmAtributos == 2) {
                                windowEjecutarDos.findById('mensaje1').value = descripcionReporte;
                                windowEjecutarDos.show();
                            } else {
                                if (nmAtributos == 4) {
                                    windowEjecutar4.findById('reporteAtributos4').value = descripcionReporte;
                                    windowEjecutar4.show();


                                } else {
                                    windowEjecutar.findById('mensaje').value = descripcionReporte;
                                    windowEjecutar.show();
                                }
                            }
                        } else {
                            Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');

                        }
                    }
                },
                {
                    text: 'Regresar',
                    handler: function() {
                        AgregarGraficonWindow.hide();
                        /*grid2.destroy();
                         createGrid();
                         reloadGrid();*/
                    }
                }
            ]
        });
        return AgregarGraficonWindow;
    };

    /****TABLAAAA***/
    var reporte = new Ext.form.TextField({               // Campo de texto de reporte en buscar
        fieldLabel: '<span style="color:#ffffff;font-size:14px;font-family:Arial,Helvetica,sans-serif;">2</span>Reporte',
        labelAlign: 'right',
        name: 'prueba',
        width: 400,
        maxLength: 100,
        enableKeyEvents : true,
        autoCreate : {
            tag : 'input',
            type : 'text',
            maxlength : 101
        }

    });

    var busquedaForm = new Ext.form.FormPanel({
        renderTo: 'formBusqueda',
        id:'reportes',
        title: '<span style="color:black;font-size:14px;">Ejecutar Reportes</span>',
        bodyStyle: 'background: white',
        buttonAlign: "bottom",
        labelAlign: 'right',
        frame: true,
        url: '',
        width: 700,
        heigth: 130,
        items:[
            {
                title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                bodyStyle: 'background: white',
                labelWidth: 75,
                layout: 'form',
                labelPad: 10,
                frame: false,
                baseCls: '',
                buttonAlign: "center",
                items: [
                    reporte
                ],
                buttons: [
                    {
                        text: 'Buscar',
                        tooltip:'Busqueda de reportes',
                        handler: function() {
                            // Entra por primera vez y crea elprimer grid
                            if (grid2 != null) {
                                grid2.destroy();
                                createGrid();
                                reloadGrid();

                            } else {
                                grid2.destroy();
                                createGrid();
                                reloadGrid();
                            }
                        }
                    },
                    {
                        text:'Cancelar',
                        handler: function() {
                            reporte.reset();
                            grid2.destroy();
                            createGrid();
                            reloadGrid();
                        }
                    }
                ]
            }
        ]
    });

    var control = false;
    var grid2;
    var selectedId;
    var sm;

    function createGrid() {
        var seleccion = true;

        Ext.PagingToolbar.prototype.onClick = function(which) {

            switch (which) {
                case "first":
                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{
                        start:desde,limit:hasta,
                        dsReporte:reporte.getValue() }});
                    break;
                case "prev":
                    desde = this.cursor - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsReporte:reporte.getValue() }});
                    //
                    break;
                case "next":
                    //
                    this.cursor = this.cursor + this.pageSize;
                    desde = this.cursor;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsReporte:reporte.getValue() }});
                    //
                    break;
                case "last":
                    desdeAux = store.getTotalCount() % this.pageSize;
                    desde = desdeAux ? (store.getTotalCount() - desdeAux) : store.getTotalCount() - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsReporte:reporte.getValue() }});
                    break;
                case "refresh":

                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                    reporte.reset();
                    break;
            }
            ;
        };

        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {
                header: "Descripci&oacute;n",
                dataIndex:'dsReporte',
                width: 350,
                sortable:true
            },
            {
                header: "Ejecutable",
                dataIndex:'nmReporte',
                width: 300,
                sortable:true
            }
            //            {header: "Atributos",     dataIndex:'numAtributos',    width: 130, sortable:true}
        ]);

        grid2 = new Ext.grid.GridPanel({
            id: 'gridGrafico',
            border:true,
            store: cargarTabla(),
            cm: cm,
            buttonAlign: 'center',
            width:700,
            height:300,
            frame:true,
            collapsible: true,
            enableColLock:false,
            stripeRows: true,
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
            renderTo:'gridElementos',
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            buttons:[
                {
                    text:'Ejecutar',
                    id : 'ejecutar',
                    tooltip:'Ejecutar reporte seleccionado',
                    handler:function() {

                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte a ejecutarse');
                        }

                    }
                },
                {
                    text:'Gr&aacute;fico',
                    id: 'grafico',
                    tooltip:'Gr&aacute;ficos del reporte seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte para ver Gr&aacute;ficos');
                        } else {

                            if (storeValidate.data.items[0].data.cdReporte == "0") {
                                Ext.MessageBox.alert('Gr&aacute;fico', 'El reporte seleccionado no tiene gr&aacute;ficos asociados');
                            } else {
                                comboGrafico().show();

                            }
                        }

                    }
                }
            ],
            bbar: new Ext.PagingToolbar({
                pageSize:itemsPerPage,
                store: store,
                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
                emptyMsg: getLabelFromMap('400012', helpMap, 'No hay registros para visualizar')

            }),
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                waitMsg: getLabelFromMap('400017', helpMap, 'Espere por favor...'),
                listeners: {
                    rowselect: function(sm, row, rec) {
                        Ext.getCmp('grafico').setDisabled(true);
                        codReporte = store.data.items[row].data.cdReporte;
                        descripcionReporte = store.data.items[row].data.dsReporte;
                        nmReporte = store.data.items[row].data.nmReporte;
                        //                        nmAtributos = store.data.items[row].data.numAtributos;
                        myMask.show();
                        storeAtributos.load(
                        {params:{cdReporte: codReporte}}
                                ); //BUSCA LA CANTIDAD DE ATRIBUTOS POR REPORTE
                        storeValidate.load(
                        {params:{cdReporte: codReporte}}
                                ); // s¡valida si tiene graficos el rpeorte

                        seleccion = false;
                        selectedId = store.data.items[row].id;
                        ejecutarForm.getForm().reset(); //limpia los forms
                        ejecutarForm2.getForm().reset();


                        Ext.getCmp('ejecutar').on('click', function() {
                            nmAtributos = storeAtributos.getTotalCount();
                            if (nmAtributos == 2) {
                                windowEjecutarDos.show();
                                Ext.getCmp('ejecutarForm2').getForm().loadRecord(rec);

                            } else {
                                if (nmAtributos == 4) {
                                    windowEjecutar4.show();
                                    Ext.getCmp('ejecutarForm4').getForm().loadRecord(rec);

                                } else {
                                    windowEjecutar.show();
                                    Ext.getCmp('ejecutarForm1').getForm().loadRecord(rec);
                                }

                            }

                        });
                        Ext.getCmp('grafico').on('click', function() {
                            seleccion = false;  //control
                            nmAtributos = storeAtributos.getTotalCount();
                            if (nmAtributos == 2) {
                                Mensaje1.setValue(descripcionReporte);

                            } else {
                                if (nmAtributos == 4) {
                                    reporteAtributos4.setValue(descripcionReporte);

                                } else {
                                    Mensaje.setValue(descripcionReporte);
                                }

                            }//Termina el else de nmAtributos
                        });
                    }
                }

            })
        });
        grid2.render();
    }

    busquedaForm.render();
    createGrid();
    /*store.load({params:{
     start:0,limit:100,
     dsReporte:reporte.getValue()
     }});*/

    function reloadGrid() {
        var _storeb = grid2.store;
        _storeb.baseParams = grid2.baseParams || {};

        _storeb.reload({
            params: {start:0,limit:100,
                dsReporte:reporte.getValue(),

                callback: function (r, options, success) {
                    if (!success) {

                        _storeb.removeAll();
                    }
                }
            }});
    }

    ;
});


