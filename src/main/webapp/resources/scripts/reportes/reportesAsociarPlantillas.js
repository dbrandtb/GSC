var helpMap = new Map();
Ext.onReady(function() {    /////////////// EMPIEZA EL FUNCIONAMIENTO DE EXT
    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";
    //var itemsPerPage=100;
    //var limit;
    var desde;
    var hasta;
    var desdeAux;

    function cargaComboPlantilla() {
        url = 'reportesPrincipal/obtenerListaPlantilla.action';
        store = new Ext.data.Store({

            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'plantillas_reportes',
                totalProperty: 'totalCount',
                id: 'totales'
            },
                    [
                        {
                            name: 'cdPlantilla',
                            type: 'string',
                            mapping:'cdPlantilla'
                        },
                        {
                            name: 'dsPlantilla',
                            type: 'string',
                            mapping:'dsPlantilla'
                        }
                    ]),
            remoteSort: true
        });
        store.setDefaultSort('totales', 'desc');
        store.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });
        return store;
    }

    ;

    function cargaComboCorporativo() {
        url = 'reportesPrincipal/obtenerListaCorporativo.action';
        store = new Ext.data.Store({

            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'plantillas_reportes',
                totalProperty: 'totalCount',
                id: 'totales'
            },
                    [
                        {
                            name: 'cdCorporativo',
                            type: 'string',
                            mapping:'cdCorporativo'
                        },
                        {
                            name: 'dsCorporativo',
                            type: 'string',
                            mapping:'dsCorporativo'
                        }
                    ]),
            remoteSort: true
        });
        store.setDefaultSort('totales', 'desc');
        store.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });

        return store;
    }

    ;


    function cargaComboAseguradora() {

        url = 'reportesPrincipal/obtenerListaAseguradora.action';
        store = new Ext.data.Store({

            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'plantillas_reportes',
                totalProperty: 'totalCount',
                id: 'totales'
            },
                    [
                        {
                            name: 'cdAseguradora',
                            type: 'string',
                            mapping:'cdAseguradora'
                        },
                        {
                            name: 'dsAseguradora',
                            type: 'string',
                            mapping:'dsAseguradora'
                        }
                    ]),
            remoteSort: true
        });
        store.setDefaultSort('totales', 'desc');
        store.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });
        return store;
    }

    ;

    function cargaComboProducto() {

        url = 'reportesPrincipal/obtenerListaProducto.action';
        store = new Ext.data.Store({

            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'plantillas_reportes',
                totalProperty: 'totalCount',
                id: 'totales'
            },
                    [
                        {
                            name: 'cdProducto',
                            type: 'string',
                            mapping:'cdProducto'
                        },
                        {
                            name: 'dsProducto',
                            type: 'string',
                            mapping:'dsProducto'
                        }
                    ]),
            remoteSort: true
        });
        store.setDefaultSort('totales', 'desc');
        store.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });
        return store;
    }

    ;

    function cargaComboReporte() {

        url = 'reportesPrincipal/obtenerListaReporte.action';
        store = new Ext.data.Store({

            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'reportes',
                totalProperty: 'totalCount',
                id: 'totales'
            },
                    [
                        {
                            name: 'cdReporte',
                            type: 'string',
                            mapping:'cdReporte'
                        },
                        {
                            name: 'dsReporte',
                            type: 'string',
                            mapping:'dsReporte'
                        }
                    ]),
            remoteSort: true
        });
        store.setDefaultSort('totales', 'desc');
        store.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });
        return store;
    }

    ;

    //Crea el Store
    function crearGridStore() {
        store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_BUSCAR
            }),
            reader: new Ext.data.JsonReader({
                root:'plantillas_reportes2',
                totalProperty: 'totalCount',
                successProperty : '@success'
            }, [
                {
                    name: 'cdPlantilla',
                    type: 'string',
                    mapping:'cdPlantilla'
                },
                {
                    name: 'cdCorporativo',
                    type: 'string',
                    mapping:'cdCorporativo'
                },
                {
                    name: 'cdAseguradora',
                    type: 'string',
                    mapping:'cdAseguradora'
                },
                {
                    name: 'cdProducto',
                    type: 'string',
                    mapping:'cdProducto'
                },
                {
                    name: 'cdReporte',
                    type: 'string',
                    mapping:'cdReporte'
                },
                {
                    name: 'dsPlantilla',
                    type: 'string',
                    mapping:'dsPlantilla'
                },
                {
                    name: 'dsCorporativo',
                    type: 'string',
                    mapping:'dsCorporativo'
                },
                {
                    name: 'dsAseguradora',
                    type: 'string',
                    mapping:'dsAseguradora'
                },
                {
                    name: 'dsProducto',
                    type: 'string',
                    mapping:'dsProducto'
                },
                {
                    name: 'dsReporte',
                    type: 'string',
                    mapping:'dsReporte'
                }

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
                }

            }
        });

        return store;
    }

    ;
    //Fin Crea el Store


    var plantilla = new Ext.form.TextField({               // Campo de texto de plantilla en buscar
        fieldLabel: 'Plantilla',
        emptyText:'',
        name : 'dsPlantillaB',
        //value : descripcionPlantilla,
        width: 400
    });

    var corporativo = new Ext.form.TextField({               // Campo de texto de corporativo en buscar
        fieldLabel: 'Corporativo',
        emptyText:'',
        name: 'dsCorporativoB',
        width: 400
    });

    var aseguradora = new Ext.form.TextField({               // Campo de texto de aseguradora en buscar
        fieldLabel: 'Aseguradora',
        emptyText:'',
        name: 'dsAseguradoraB',
        width: 400
    });

    var producto = new Ext.form.TextField({               // Campo de texto de producto en buscar
        fieldLabel: 'Producto',
        emptyText:'',
        name: 'dsProductoB',
        width: 400
    });

    var reporte = new Ext.form.TextField({               // Campo de texto de reporte en buscar
        fieldLabel: 'Reporte',
        emptyText:'',
        name: 'dsReporteB',
        width: 400
    });


    var bool = true;           // Para controlar el grid

    var busquedaForm = new Ext.form.FormPanel({
        renderTo: 'formBusqueda',
        id:'reportes',
        title: '<span style="color:black;font-size:14px;">Asociar Plantilla a Reporte</span>',
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
                    plantilla,corporativo, aseguradora, producto, reporte
                ],
                buttons: [
                    {
                        text: 'Buscar',
                        handler: function() {
                            if (grid2 != null) {
                                reloadGrid();

                            } else {
                                createGrid();
                            }


                            // Entra por primera vez y crea elprimer grid
                            //grid2.destroy();
                            //createGrid();
                        }
                    },
                    {
                        text:'Cancelar',
                        handler: function() {
                            //                    plantilla.reset();
                            //                    corporativo.reset();
                            //                    aseguradora.reset();
                            //                    producto.reset();
                            //                    reporte.reset();
                            grid2.destroy();
                            createGrid();
                            reloadGrid();

                        }
                    }
                ]
            }
        ]
    });

    //    busquedaForm.render('formBusqueda');
    /**
     *
     * Funcion agregar
     */


    var plantillaA = new Ext.form.ComboBox({
        fieldLabel: 'Plantilla',
        id: 'plantilla_combo',
        hiddenName: 'cdPlantilla',
        width: 250,
        forceSelection: true,
        triggerAction: 'all',
        store:cargaComboPlantilla(),
        mode: 'local',
        displayField: 'dsPlantilla',
        valueField: 'cdPlantilla',
        editable:true,
        allowBlank: false,
        //        readOnly :true,
        anchor:'95%',
        //        typeAhead: true,
        emptyText:'Seleccione una Plantilla...'
    });

    var corporativoA = new Ext.form.ComboBox({
        fieldLabel: 'Corporativo',
        hiddenName: 'cdCorporativo',
        width: 250,
        autoComplete: true,
        forceSelection: true,
        triggerAction: 'all',
        store:cargaComboCorporativo(),
        mode: 'local',
        displayField: 'dsCorporativo',
        valueField: 'cdCorporativo',
        allowBlank: false,
        editable:true,
        //        readOnly :false,
        anchor:'95%',
        //        typeAhead: false,
        emptyText:'Seleccione un Corporativo...'
    });


    var aseguradoraA = new Ext.form.ComboBox({
        fieldLabel: 'Aseguradora',
        hiddenName: 'cdAseguradora',
        width: 250,
        forceSelection: true,
        triggerAction: 'all',
        store:cargaComboAseguradora(),
        mode: 'local',
        displayField:'dsAseguradora',
        valueField : 'cdAseguradora',
        editable:true,
        allowBlank: false,
        //        readOnly :true,
        anchor:'95%',
        //                typeAhead: true,
        emptyText:'Seleccione una Aseguradora...'
    });

    var productoA = new Ext.form.ComboBox({
        fieldLabel: 'Producto',
        hiddenName: 'cdProducto',
        id:'unitField',
        width: 250,
        allowBlank: false,
        forceSelection: true,
        store:cargaComboProducto(),
        triggerAction: 'all',
        mode: 'local',
        displayField:'dsProducto',
        anchor:'95%',
        editable:true,
        valueField : 'cdProducto',
        emptyText:'Seleccione un Producto...'
    });


    var reporteA = new Ext.form.ComboBox({
        fieldLabel: 'Reporte',
        hiddenName: 'cdReporte',
        width: 250,
        allowBlank: false,
        store:cargaComboReporte(),
        //        typeAhead: true,
        forceSelection: true,
        triggerAction: 'all',
        mode: 'local',
        displayField:'dsReporte',
        valueField : 'cdReporte',
        editable:true,
        //        readOnly :true,
        anchor:'95%',
        emptyText:'Seleccione un Reporte...'


    });

    var generaA = new Ext.form.Checkbox({
        fieldLabel: 'Genera Gr&aacute;fico',
        width: 50
    });

    var plantillaACod = new Ext.form.TextField({
        name: 'cdPlantilla',
        hidden: true
    });

    var corporativoACod = new Ext.form.TextField({
        name: 'cdCorporativo',
        hidden: true
    });

    var aseguradoraACod = new Ext.form.TextField({
        name: 'cdAseguradora',
        hidden: true
    });

    var productoACod = new Ext.form.TextField({
        name: 'cdProducto',
        hidden: true
    });

    var reporteACod = new Ext.form.TextField({
        name: 'cdReporte',
        hidden: true
    });

    var agregaForm = new Ext.form.FormPanel({
        id: 'agregaForm',
        labelAlign: 'top',
        baseCls: 'x-plain',
        items: [plantillaA,corporativoA,aseguradoraA,productoA,reporteA,plantillaACod,corporativoACod,aseguradoraACod,productoACod,reporteACod]
    });

    var insertarPlantilla = new Ext.Window({
        title: "Asociar Plantilla a Reporte",
        width: 400,
        height:320,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [agregaForm] ,
        buttons: [
            {
                text: 'Guardar',
                handler: function() {


                    agregaForm.form.url = 'reportesPrincipal/asociarPlantillaReporte.action' +
                                          '?dsPlantilla=' + plantillaA.selectedIndex +
                                          '&dsCorporativo=' + corporativoA.selectedIndex +
                                          '&dsAseguradora=' + aseguradoraA.selectedIndex +
                                          '&dsProducto=' + productoA.selectedIndex +
                                          '&dsReporte=' + reporteA.selectedIndex;


                    if (agregaForm.form.isValid()) {
                        agregaForm.form.submit({
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Asociaci&oacute;n No Creado', "No se pudo crear la asociaci&oacute;n "/*action.result.errorInfo*/);//TODO comentado por prueba
                                insertarPlantilla.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();

                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Plantilla Asociada exitosamente', 'Se ha ingresado una nueva asociaci&oacute;n de plantilla');
                                insertarPlantilla.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();

                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    }
                }
            },
            {
                text: 'Regresar',
                handler: function() {
                    insertarPlantilla.hide();

                }
            }
        ]
    });


    /*************************************
     *Crea la ventana de editar
     **************************************/
    var plantillaB = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Plantilla',
        name: 'cdPlantilla',
        width: 70,
        disabled:true
    });

    var plantilla1B = new Ext.form.TextField({
        fieldLabel: 'Desc. Plantilla',
        name: 'dsPlantilla',
        width: 300,
        disabled:true
    });


    var corporativoB = new Ext.form.ComboBox({
        forceSelection: true,
        fieldLabel: 'Corporativo',
        labelAlign: 'right',
        hiddenName:'cdCorporativo',
        store: cargaComboCorporativo(),
        anchor:'98%',
        displayField:'dsCorporativo',
        allowBlank: false,
        valueField: 'cdCorporativo',
        mode: 'local',
        triggerAction: 'all',
        editable:true

    });


    var aseguradoraB = new Ext.form.ComboBox({
        forceSelection: true,
        fieldLabel: 'Aseguradora',
        labelAlign: 'right',
        hiddenName:'cdAseguradora',
        store: cargaComboAseguradora(),
        anchor:'98%',
        displayField:'dsAseguradora',
        allowBlank: false,
        valueField: 'cdAseguradora',
        mode: 'local',
        triggerAction: 'all',
        editable:true
    });

    var productoB = new Ext.form.ComboBox({
        forceSelection: true,
        fieldLabel: 'Producto',
        labelAlign: 'right',
        hiddenName:'cdProducto',
        store: cargaComboProducto(),
        anchor:'98%',
        displayField:'dsProducto',
        allowBlank: false,
        valueField: 'cdProducto',
        mode: 'local',
        triggerAction: 'all',
        editable:true
    });


    var reporteB = new Ext.form.ComboBox({
        forceSelection: true,
        fieldLabel: 'Reporte',
        labelAlign: 'right',
        hiddenName:'cdReporte',
        store: cargaComboReporte(),
        anchor:'98%',
        displayField:'dsReporte',
        allowBlank: false,
        valueField: 'cdReporte',
        mode: 'local',
        triggerAction: 'all',
        editable:true
    });
   
    var editForm = new Ext.form.FormPanel({
        id: 'editForm',
        labelAlign: 'top',
        baseCls: 'x-plain',
        items: [plantillaB,plantilla1B,corporativoB,aseguradoraB,productoB,reporteB]
    });

    var windowEditar = new Ext.Window({
        title: 'Editar Reporte',
        width: 400,
        height:360,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: editForm,
        buttons: [
            {
                text: 'Guardar',
                handler: function() {

                    editForm.form.url = 'reportesPrincipal/editarAsociarPlantillaReporte.action' +
                                        '?cdPlantilla=' + plantillaB.getValue() +
                                        '&dsCorporativo=' + corporativoB.selectedIndex +
                                        '&dsAseguradora=' + aseguradoraB.selectedIndex +
                                        '&dsProducto=' + productoB.selectedIndex +
                                        '&dsReporte=' + reporteB.selectedIndex;

                    if (editForm.form.isValid()) {
                        editForm.form.submit({
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error', 'Asociaci&oacute;n no Editada'/*, action.result.errorInfo*/);
                                windowEditar.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Asociaci&oacute;n editada exitosamente', 'Se ha editado una asociaci&oacute;n de plantilla a reporte');
                                windowEditar.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    }
                }
            },
            {
                text: 'Regresar',
                handler: function() {
                    windowEditar.hide();

                }
            }
        ]
    });

    /////// Editar//////////////////////////

    /*****se crea la ventana de borrar un plan***/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar esta asociaci&oacute;n plantilla-reporte?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });

    var plantillaC = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Plantilla',
        name: 'cdPlantilla',
        width: 70,
        hidden: true
    });

    var aseguradoraD = new Ext.form.TextField({
        fieldLabel: '',
        name: 'dsAseguradora',
        width: 70,
        hidden: true
    });

    var corporativoC = new Ext.form.TextField({
        fieldLabel: '',
        name: 'cdCorporativo',
        width: 70,
        hidden: true
    });

    var aseguradoraC = new Ext.form.TextField({
        fieldLabel: '',
        name: 'cdAseguradora',
        width: 70,
        hidden: true
    });

    var productoC = new Ext.form.TextField({
        fieldLabel: '',
        name: 'cdProducto',
        width: 70,
        hidden: true
    });

    var reporteC = new Ext.form.TextField({
        fieldLabel: '',
        name: 'cdReporte',
        width: 70,
        hidden: true
    });

    var borrarForm = new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'reportesPrincipal/borrarAsociarPlantillaReporte.action',
        items:[msgBorrar,plantillaC,corporativoC,aseguradoraD,aseguradoraC,productoC,reporteC]
    });

    var windowBorrar = new Ext.Window({
        title: 'Eliminar Asociaci&oacute;n platillas-reportes',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [
            {
                text: 'Borrar',
                handler: function() {
                    if (borrarForm.form.isValid()) {
                        borrarForm.form.submit({
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error Borrando la asociaci&oacute;n', 'Ha ocurrido un error al borrar la asociaci&oacute;n');
                                windowBorrar.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Asociaci&oacute;n Borrada', 'La asociaci&oacute;n de plantillas - reporte ha sido borrado satisfactoriamente');
                                windowBorrar.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }
                }
            },
            {
                text: 'Cancelar',
                handler: function() {
                    windowBorrar.hide();

                }
            }
        ]
    });
    /*****/


    /****TABLAAAA***/

    var control = false;
    var grid2;
    var selectedId;
    var sm;
    var store;
    var storeCount;


    function createGrid() {
        var seleccion = true;
        Ext.PagingToolbar.prototype.onClick = function(which) {
            switch (which) {
                case "first":
                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{
                        start:desde,limit:hasta,
                        dsPlantilla:plantilla.getValue(),
                        dsCorporativo:corporativo.getValue(),
                        dsAseguradora:aseguradora.getValue(),
                        dsProducto:producto.getValue(),
                        dsReporte:reporte.getValue() }});
                    break;
                case "prev":
                    desde = this.cursor - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsPlantilla:plantilla.getValue(),
                        dsCorporativo:corporativo.getValue(),
                        dsAseguradora:aseguradora.getValue(),
                        dsProducto:producto.getValue(),
                        dsReporte:reporte.getValue() }});
                    //
                    break;
                case "next":
                    //
                    this.cursor = this.cursor + this.pageSize;
                    desde = this.cursor;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsPlantilla:plantilla.getValue(),
                        dsCorporativo:corporativo.getValue(),
                        dsAseguradora:aseguradora.getValue(),
                        dsProducto:producto.getValue(),
                        dsReporte:reporte.getValue() }});
                    //
                    break;
                case "last":
                    //
                    desdeAux = store.getTotalCount() % this.pageSize;
                    desde = desdeAux ? (store.getTotalCount() - desdeAux) : store.getTotalCount() - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsPlantilla:plantilla.getValue(),
                        dsCorporativo:corporativo.getValue(),
                        dsAseguradora:aseguradora.getValue(),
                        dsProducto:producto.getValue(),
                        dsReporte:reporte.getValue() }});
                    break;
                case "refresh":

                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                    plantilla.reset();
                    corporativo.reset();
                    aseguradora.reset();
                    producto.reset();
                    reporte.reset();
                    grid2.destroy();
                    createGrid();

                    break;
            }
            ;
        };

        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {
                header: "Plantilla",
                dataIndex:'dsPlantilla',
                width: 100,
                sortable:true
            },
            {
                header: "Corporativo",
                dataIndex:'dsCorporativo',
                width: 150,
                sortable:true
            },
            {
                header: "Aseguradora",
                dataIndex:'dsAseguradora',
                width: 150,
                sortable:true
            },
            {
                header: "Producto",
                dataIndex:'dsProducto',
                width: 150,
                sortable:true
            },
            {
                header: "Reporte",
                dataIndex:'dsReporte',
                width: 150,
                sortable:true
            }

        ]);

        grid2 = new Ext.grid.GridPanel({
            id:'grid2',
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
            renderTo:'gridElementos',
            store: crearGridStore(),
            buttonAlign: 'center',
            width:700,
            height:300,
            border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            frame:true,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                itemsPerPage:100,
                pageSize:100,
                store: store,
                emptyMsg: getLabelFromMap('400012', helpMap, 'No hay registros para visualizar'),
                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}')

            }),
            buttons:[
                {
                    id:'agregar',
                    text:'Agregar',
                    tooltip:'Agregar una nueva opcion',
                    handler:function() {
                        insertarPlantilla.show();
                    }
                },
                {
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar Asociaci&oacute;n de Plantillas seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione la Plantilla a editar');
                        }
                    }
                },
                {
                    text:'Borrar',
                    id:'borrar',
                    tooltip:'Borra opcion seleccionada',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione la Plantilla a borrar');
                        }
                    }
                },
                {
                    text:'Exportar',
                    tooltip:'Exporta Asociar Plantilla',
                    handler:function() {
                        showExportDialog(_ACTION_EXPORT + '?dsPlantilla=' + plantilla.getValue() +
                                         '&dsCorporativo=' + corporativo.getValue() +
                                         '&dsAseguradora=' + aseguradora.getValue() +
                                         '&dsProducto=' + producto.getValue() +
                                         '&dsReporte=' + reporte.getValue() +
                                         '&descripcion=AsociarPlantillaGraficos')
                    }

                }
            ],
            viewConfig: {
                autoFill: true,
                forceFit:true
            },
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        seleccion = false;
                        selectedId = store.data.items[row].id;

                        Ext.getCmp('agregar').on('click', function() {
                            Ext.getCmp('agregaForm').getForm().loadRecord(rec);
                        });

                        Ext.getCmp('editar').on('click', function() {
                            windowEditar.show();
                            Ext.getCmp('editForm').getForm().loadRecord(rec);

                            //aux_aseguradora=reporteA.getValue();
                            //aux_corporativo=aseguradoraA.getValue();
                            //aux_reporte=reporteA.getValue();
                        });


                        Ext.getCmp('borrar').on('click', function() {
                            windowBorrar.show();
                            Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                        });
                    }
                }

            })


        });
        grid2.render();
        /*store.load({params:{
            start:0,limit:100,
            dsPlantilla:plantilla.getValue(),
            dsCorporativo:corporativo.getValue(),
            dsAseguradora:aseguradora.getValue(),
            dsProducto:producto.getValue(),
            dsReporte:reporte.getValue() }});*/

    }

    ;
    busquedaForm.render();
    createGrid();
    /* */
    function reloadGrid() {
        var _storeb = grid2.store;
        _storeb.baseParams = grid2.baseParams || {};

        _storeb.reload({
            params: {start:0,limit:100,
                dsPlantilla:plantilla.getValue(),
                dsCorporativo:corporativo.getValue(),
                dsAseguradora:aseguradora.getValue(),
                dsProducto:producto.getValue(),
                dsReporte:reporte.getValue()},
            callback: function (r, options, success) {
                if (!success) {
                    _storeb.removeAll();
                }
            }
        });
    }

    ;


});


