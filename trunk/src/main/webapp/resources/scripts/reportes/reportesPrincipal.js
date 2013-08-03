var helpMap = new Map();
Ext.onReady(function() {    /////////////// EMPIEZA EL FUNCIONAMIENTO DE EXT
    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";
    var store;
    var url;
    var codReporte;
    var dsReporte;
    var nmReporte;
    var desde;
    var hasta;
    var desdeAux;

    function cargarTabla() {

        store = new Ext.data.Store({

            proxy: new Ext.data.HttpProxy({
                // url: _ACTION_BUSCAR_REPORTES + '?dsReporte='+reporte.getValue(),
                url: _ACTION_BUSCAR_REPORTES
                //                waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
            }),
            reader: new Ext.data.JsonReader({
                root:'reportes',
                totalProperty: 'totalCount',
                successProperty : '@success'
                //                id: 'totales'
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

            ]),
            //autoLoad: true,
            listeners: {
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
                },
                remoteSort: true
            }
        });

        return store;
    }

    ;

    /**
     * Fin de los STORE
     *
     */


    var reporte = new Ext.form.TextField({               // Campo de texto de reporte en buscar
        id:'reporteId',
        fieldLabel: getLabelFromMap('reporteId', helpMap, 'Reporte'),
        tooltip: getToolTipFromMap('reporteId', helpMap, 'Buscar Reporte'),
        name: 'dsReporte',
//        maxLengthText: 100,
        maxLength: 100,
        autoCreate : {
            tag : 'input',
            type : 'text',
            maxlength : 101
        },
        hasHelpIcon: true,
        width: 500
    });

    var busquedaForm = new Ext.form.FormPanel({

        renderTo: 'formBusqueda',
        id:'reportesForm',
        title: '<span style="color:black;font-size:12px;">' + getLabelFromMap('adminReportes', helpMap, 'Administraci&oacute;n de Reportes') + '</span>',//Administraci&oacute;n de Reportes</span>',
        bodyStyle: 'background: white',
        labelAlign: 'right',
        frame : true,
        url:'reportesPrincipal/obtenerReportes.action',
        //        url:'',
        iconCls:'logo',
        buttonAlign: 'center',
        width:700,
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
                        handler: function() {
                            if (grid2 != null) {
                                reloadGrid();

                            } else {
                                createGrid();
                            }
                        }


                    },
                    {
                        text:'Cancelar',
                        handler: function() {
                            reporte.reset();
                        }
                    }
                ]
            }
        ]
    });


    //cm.defaultSortable = true;

    /**
     *
     * Funcion agregar
     */
    var agregar = function() {


        var nombreRepo = new Ext.form.TextField({
            fieldLabel: 'Nombre Reporte',
            allowBlank: false,
            maxLength :100,
            name: 'nombreRepo',
            anchor:'90%',
            listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex) {
                    var cad = trim(nombreRepo.getValue());
                    if (cad == '') {
                        nombreRepo.setValue('');
                    }
                }
            }

        });

        var ejecutable = new Ext.form.TextField({
            fieldLabel: 'Ejecutable',
            allowBlank: false,
            maxLength :20,
            //            blankText:'',
            name: 'ejecutable',
            anchor:'90%',                
            listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex) {
                    var cad = trim(ejecutable.getValue());
                    if (cad == '') {
                        ejecutable.setValue('');
                    }
                }
            }                               
        });


        var mensajeForm = new Ext.form.FormPanel({
            id: 'mensajeform',
            labelWidth: 75,
            labelAlign: 'right',
            baseCls: 'x-plain',
            url:'reportesPrincipal/agregarReportes.action',
            items: [nombreRepo,ejecutable]
            //items: [cdReporte,nombreRepo,ejecutable,grafico]
        });

        var insertaActualiRepo = new Ext.Window({

            title: "Insertar Reportes",
            layout: 'fit',
            plain: true,
            modal: true,
            closable:false,
            width: 370,
            heigth: 430,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            items: mensajeForm,
            buttons: [
                {
                    text: 'Guardar',
                    handler: function() {
                        if (mensajeForm.form.isValid()) {
                            mensajeForm.form.submit({
                                waitTitle:'Espere',
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Error al crear Reporte',  action.result.wrongMsg);
                                    insertaActualiRepo.hide();
                                    //                                grid2.destroy();
                                    //                                createGrid();
                                    reloadGrid();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Reporte Creado exitosamente', 'Se ha ingresado un nuevo reporte');
                                    insertaActualiRepo.hide();
                                    //                                grid2.destroy();
                                    //                                createGrid();
                                    //                                store.load();
                                    reloadGrid();

                                }
                            });
                        } else {
                            Ext.MessageBox.alert("Error", 'Complete la informaci&oacute;n requerida');
                        }
                    }
                },
                {
                    text: 'Regresar',
                    handler: function() {
                        insertaActualiRepo.hide();
                    }
                }
            ]
        });
        insertaActualiRepo.show();
    };

    /*************************************
     *Crea la ventana de editar
     **************************************/


    var dsCdReporte = new Ext.form.TextField({
        fieldLabel: '',
        allowBlank: false,
        name:'cdReporte',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true,
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(dsCdReporte.getValue());
                if (cad == '') {
                    dsCdReporte.setValue('');
                }
            }
        }
    });

    var dsDsReporte = new Ext.form.TextField({
        fieldLabel: 'Nombre Reporte',
        allowBlank: false,
        maxLength:100,
        name: 'dsReporte',
        anchor:'90%',
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(dsDsReporte.getValue());
                if (cad == '') {
                    dsDsReporte.setValue('');
                }
            }
        }
    });

    var dsNmReporte = new Ext.form.TextField({
        id: 'editEjecutable',
        fieldLabel: 'Ejecutable',
        allowBlank: false,
        readOnly:true,
        disabled:true,
        name: 'nmReporte',
        anchor:'90%',
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(dsNmReporte.getValue());
                if (cad == '') {
                    dsNmReporte.setValue('');
                }
            }
        }
    });

    var editForm = new Ext.FormPanel({
        id:'editForm',
        labelWidth: 75,
        labelAlign: 'right',
        baseCls: 'x-plain',
        url:'reportesPrincipal/editarReportes.action',
        items:[dsDsReporte, dsNmReporte,dsCdReporte]
    });

    var windowEditar = new Ext.Window({
        title: 'Editar Reporte',
        width: 400,
        height:165,
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
                    if (editForm.form.isValid()) {
                        editForm.form.submit({
                            params:{
                                editReporte:Ext.getCmp('editEjecutable').getValue()
                            },
                            waitMsg:'Procesando...',
                            failure: function(form, action) {

                                //                            if (dsDsReporte.getValue().length > 20) {
                                //                                Ext.MessageBox.alert('Error al crear Reporte', 'El tamaño permitido es de: ' + dsDsReporte.maxLength);
                                //                            } else {
                                Ext.MessageBox.alert('Error en el reporte ', action.result.errorInfo);
                                //                            }

                                windowEditar.hide();
                                reloadGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Reporte Editado', 'El reporte ha sido editado satisfactoriamente');
                                windowEditar.hide();
                                grid2.destroy();
                                createGrid();
                                //                            reloadGrid();

                            }
                        });
                    } else {
                        Ext.MessageBox.alert("Error", 'Complete la informaci&oacute;n requerida');
                        //                     Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
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
        fieldLabel:' Esta seguro que desea Eliminar este reporte?',
        labelSeparator:'',
        anchor:'90%',
        hidden : true
    });

    var cdReporte = new Ext.form.TextField({
        fieldLabel: '',
        name:'cdReporte',
        anchor:'90%',
        labelSeparator:'',
        hidden : true
    });

    var borrarForm = new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'reportesPrincipal/borrarReportes.action',
        items:[ msgBorrar, cdReporte]
    });

    var windowBorrar = new Ext.Window({
        title: 'Eliminar Reporte',
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
                text: 'Eliminar',
                handler: function() {
                    if (borrarForm.form.isValid()) {
                        borrarForm.form.submit({
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error Eliminando Reporte', 'Ha ocurrido un error al eliminar el reporte');
                                windowBorrar.hide();
                                grid2.destroy();
                                createGrid();
                                //                            reloadGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Reporte Eliminado', 'El reporte  ha sido eliminado satisfactoriamente');
                                windowBorrar.hide();
                                grid2.destroy();
                                createGrid();
                                //                            reloadGrid();

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
    ////////////////////////////////////////////////////
    busquedaForm.render();
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
                    //
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
                    grid2.destroy();
                    createGrid();

                    break;
            }
            ;
        };

        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            // {header: "Seleccionar",  dataIndex:'num', width: 50, sortable:true, locked:false, id:'num'},
            // {header: getLabelFromMap('codigoId', helpMap, 'C&oacute;digo'), dataIndex:'cdReporte', width: 50, sortable:true},
            // {header: getLabelFromMap('descripcionRep', helpMap, 'Descripci&oacute;n'), dataIndex:'dsReporte', width: 270, sortable:true},
            // {header: getLabelFromMap('ejecutableRep', helpMap, 'Ejecutable'),  dataIndex:'nmReporte',    width: 70, sortable:true}
            {
                header:  "C&oacute;digo",
                dataIndex:'cdReporte',
                width: 50,
                sortable:true
            },
            {
                header:  "Descripci&oacute;n",
                dataIndex:'dsReporte',
                width: 270,
                sortable:true
            },
            {
                header:  "Ejecutable",
                dataIndex:'nmReporte',
                width: 70,
                sortable:true
            }

        ]);
        grid2 = new Ext.grid.GridPanel({

            id: 'grid2',
            border:true,
            renderTo:'gridElementos',
            store: cargarTabla(),
            cm: cm,
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            buttonAlign: 'center',
            width:700,
            height:300,
            frame:true,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                pageSize:itemsPerPage,
                store: store,
                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
                emptyMsg: getLabelFromMap('400012', helpMap, 'No hay registros para visualizar')
            }),
            buttons:[
                {
                    text:'Agregar',
                    tooltip:'Agregar un nuevo reporte',
                    handler:function() {
                        agregar();
                    }
                },
                {
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar Reporte seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte a editar');
                        }
                    }
                },
                {
                    text:'Eliminar',
                    id:'borrar',
                    tooltip:'Eliminar Reporte seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte a eliminar');
                        }
                    }
                },
                {
                    text: 'Atributos',
                    id:'atributos',
                    tooltip: 'Atributos del Reporte seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte para sus atributos');
                        }

                    }
                },
                {
                    id:'productos',
                    text:'Productos',
                    tooltip:'Productos del Reporte seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte para sus productos');
                        }
                    }
                },
                {
                    id:'grafico',
                    text:'Gr&aacute;fico',
                    tooltip:'Graficos del Reporte seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione el reporte para sus gr&aacute;ficos');
                        }
                    }
                },
                {
                    text:'Exportar',
                    tooltip:'Exportar Reportes',
                    handler:function() {
                        showExportDialog(_ACTION_EXPORT + '?dsReporte=' + reporte.getValue() + '&descripcion=Reportes');
                    }
                }
            ],
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        seleccion = false;
                        selectedId = store.data.items[row].id;
                        codReporte = store.data.items[row].data.cdReporte;
                        dsReporte = store.data.items[row].data.dsReporte;
                        nmReporte = store.data.items[row].data.nmReporte;

                        Ext.getCmp('editar').on('click', function() {
                            windowEditar.show();
                            Ext.getCmp('editForm').getForm().loadRecord(rec);
                        });
                        Ext.getCmp('borrar').on('click', function() {
                            windowBorrar.show();
                            Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                        });

                        Ext.getCmp('grafico').on('click', function() {
                            window.location.replace('reportesPrincipal/desplegarGrafico.action?dsReporte=' + dsReporte + '&cdReporte=' + codReporte);

                        });

                        Ext.getCmp('productos').on('click', function() {
                            window.location.replace('reportesPrincipal/desplegarProductos.action?dsReporte=' + dsReporte + '&cdReporte=' + codReporte);

                        });

                        Ext.getCmp('atributos').on('click', function() {
                            window.location.replace('reportesPrincipal/desplegarAtributos.action?dsReporte=' + dsReporte + '&cdReporte=' + codReporte + '&nmReporte=' + nmReporte);

                        });
                    }
                }

            }),
            viewConfig: {
                autoFill: true,
                forceFit:true
            }
        });
        grid2.render();
        /*store.load({params:{start:0, limit:50}});*/

    }

    ;

    busquedaForm.render();
    createGrid();


    function reloadGrid() {

        var _storeb = grid2.store;
        _storeb.baseParams = grid2.baseParams || {};

        _storeb.reload({
            params: {start:0,limit:50,
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