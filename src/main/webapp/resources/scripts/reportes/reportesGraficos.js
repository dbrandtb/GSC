Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = "side";


    /**
     * store provisional
     */
    var url;
    var desde;
    var hasta;
    var desdeAux;
    var nmReporte = ' ';
    //var store;
    //  function cargarTabla() {

    url = _ACTION_BUSCAR_GRAFICOS;       //Mapeo con struts
    var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: url
        }),
        reader: new Ext.data.JsonReader({
            root:'graficoTabla',
            totalProperty: 'totalCount',
            id: 'totales'
        }, [
            {
                name: 'nmGrafico',
                type: 'string',
                mapping:'nmGrafico'
            },
            {
                name: 'nmGraficoAux',
                type: 'string',
                mapping:'nmGrafico'
            }
            ,
            {
                name: 'descripcion',
                type: 'string',
                mapping:'descripcion'
            }

        ])
    });


    //      return store;
    // };

    /////combo
    var storeCombo;

    function cargarCombo() {
        storeCombo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_CARGAR_COMBO
            }),
            reader: new Ext.data.JsonReader({
                root:'combo',
                totalProperty: 'totalCountCombo',
                id: 'totalesCombo'
            }, [
                {
                    name: 'nmGrafico',
                    type: 'string',
                    mapping:'codigo'
                },
                {
                    name:  'descripcion',
                    type: 'string',
                    mapping:'descripC'
                },

            ]),
            remoteSort: true
        });
        storeCombo.setDefaultSort('totalesCombo', 'desc');
        storeCombo.load(Ext.data.Store.prototype.updateSnapshot = function () {
            (this.data = this.snapshot)
        });

        return storeCombo;
    }

    ;

    /******************
     *Form de busqueda para el grid.
     *******************/

    var codigoText = new Ext.form.TextField({
        fieldLabel: '<span style="color:#ffffff;font-size:14px;font-family:Arial,Helvetica,sans-serif;">1</span>C&oacute;digo',
        name : 'codRep',
        value : codRep,
        disabled : true,
        width: 50
    });

    var descripcion = new Ext.form.TextField({
        fieldLabel: 'Descripcion',
        name : 'descripcion',
        value : descripcionAtri,
        disabled : true,
        width: 450
    });


    var atributosForm = new Ext.form.FormPanel({
        title: '<span style="color:black;font-size:14px;">Administraci&oacute;n de Gr&aacute;ficos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        //      url:_ACTION_MANTTO,
        width: 700,
        height:120,
        items: [
            {
                layout:'form',
                border: false,
                items:[
                    {
                        title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                        bodyStyle:'background: white',
                        labelWidth: 70,
                        layout: 'form',
                        frame:true,
                        baseCls: '',
                        items:[
                            codigoText,
                            descripcion
                        ]
                    }
                ]
            }
        ]
    });
    atributosForm.render(document.body);

    var sm = new Ext.grid.RowNumberer;
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {
            header: "Nombre de Gr&aacute;fico",
            dataIndex:'nmGrafico',
            width: 120,
            sortable:true
        },
        {
            header: "Tipo de Gr&aacute;fico",
            dataIndex:'descripcion',
            width:120,
            sortable:true
        }

    ]);

    //  cm.defaultSortable = true;

    var grid2;
    var selectedId;

    /**
     *
     * Funcion agregar
     */
    var agregarGrafico = function() {

        var nombreGrafico = new Ext.form.TextField({
            fieldLabel: 'Gr&aacute;fico',
            //            blankText :'Este campo es requerido',
            allowBlank: false,
            maxLength :50,
            name: 'nmGrafico',
            width: 200,
            listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex) {
                    var cad = trim(nombreGrafico.getValue());
                    if (cad == '') {
                        nombreGrafico.setValue('');
                    }
                }
            }
        });

        var tipoGrafico = new Ext.form.ComboBox({

            //            typeAhead: true,

            // id:'formatoCombo',
            fieldLabel: 'Tipo',
            labelAlign: 'right',
            hiddenName:'grafico',
            store: cargarCombo(),
            displayField:'descripcion',
            allowBlank: false,
            autoComplete: true,
            editable:true,
            forceSelection: true,
            valueField: 'nmGrafico',
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Selecciona un formato...'

        });

        var agregarGraficoForm = new Ext.form.FormPanel({
            id:'agregarGraficoForm',
            labelAlign: 'right' ,
            baseCls: 'x-plain',
            columnWidth: 3,
            url:'reportesPrincipal/agregarGrafico.action?cdReporte=' + codigoText.getValue(),
            // url:'reportesPrincipal/borrarGrafico.action',
            items: [nombreGrafico,tipoGrafico]

        });

        var AgregarGraficonWindow = new Ext.Window({

            title: "Agregar Gr&aacute;fico",
            layout: 'fit',
            plain: true,
            modal: true,
            width: 350,
            heigth: 400,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            items: agregarGraficoForm,
            buttons: [
                {
                    text: 'Guardar',
                    handler: function() {
                        if (agregarGraficoForm.form.isValid()) {
                            agregarGraficoForm.form.submit({
                                waitTitle:'Espere',
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Error al agregar Gr&aacute;fico', 'El Gr&aacute;fico no pudo ser agregado');
                                    AgregarGraficonWindow.hide();
                                    //                                    reloadGrid();
                                    grid2.destroy();
                                    createGrid();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Gr&aacute;fico agregado exitosamente', 'Se ha Agregado un nuevo Gr&aacute;fico');
                                    AgregarGraficonWindow.hide();
                                    //                                    reloadGrid();
                                    grid2.destroy();
                                    createGrid();
                                }
                            });
                        } else {
                            Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                        }
                    }

                },
                {
                    text: 'Regresar',
                    handler: function() {
                        AgregarGraficonWindow.hide();
                    }
                }
            ]
        });
        AgregarGraficonWindow.show();
    };
    /**
     * Editar Grafico
     */


    var nombreEditarGrafico = new Ext.form.TextField({
        fieldLabel: 'Gr&aacute;fico',
        id:'nombreEditarGrafico',
        disabled : true,
        name: 'nmGrafico',
        hasHelpIcon: true,
        allowBlank: false,
        readOnly:true,
        width: 200,
        hidden : false,
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(nombreEditarGrafico.getValue());
                if (cad == '') {
                    nombreEditarGrafico.setValue('');
                }

            }
        }
    });

    var tipoEditarGrafico = new Ext.form.ComboBox({
        id:'formatoEditarCombo',
        name:'formatoEditarCombo',
        fieldLabel: 'Tipo',
        labelAlign: 'right',
        hiddenName:'grafico',
        store: cargarCombo(),
        displayField:'descripcion',
        allowBlank: false,
        valueField: 'nmGrafico',
        autoComplete: true,
        editable:true,
        forceSelection: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Selecciona un formato...',
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(tipoEditarGrafico.getValue());
                if (cad == '') {
                    tipoEditarGrafico.setValue('');
                }

            }
        }
    });

    var editarGraficoForm = new Ext.form.FormPanel({
        name:'editarGrafform',
        id: 'editarGrafform',
        labelAlign: 'right' ,
        baseCls: 'x-plain',
        columnWidth: 1,
        url:'reportesPrincipal/editarGrafico.action?cdReporte=' + codigoText.getValue(),
//        url:'reportesPrincipal/editarGrafico.action?cdReporte=' + codigoText.getValue()+'&nmGrafico='+ Ext.getCmp('editarGrafform').form.findField('nmGrafico').getValue(),
        items: [nombreEditarGrafico,tipoEditarGrafico],
         listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(tipoEditarGrafico.getValue());
                if (cad == '') {
                    tipoEditarGrafico.setValue('');
                }

            }
        }

    });

    var windowEditarGrafico = new Ext.Window({

        title: "Editar Gr&aacute;fico",
        layout: 'fit',
        plain: true,
        modal: true,
        width: 350,
        heigth: 400,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: editarGraficoForm,
        buttons: [
            {
                text: 'Guardar',
                handler: function() {

                    if (editarGraficoForm.form.isValid()) {
                        editarGraficoForm.form.submit({
                            params:{
                                nmGrafico:Ext.getCmp('nombreEditarGrafico').getValue()
//                                grafico: Ext.getCmp('editarGraficoForm').form.findField('nombreEditarGrafico').getValue()
//                                grafico:Ext.getCmp('formatoEditarCombo').valueField
                            },
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error al editar Gr&aacute;fico', 'El Gr&aacute;fico no pudo ser editado');
                                windowEditarGrafico.hide();
                                //                            reloadGrid();
                                grid2.destroy();
                                createGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Gr&aacute;fico editado exitosamente', 'Se ha editado un nuevo Gr&aacute;fico');
                                windowEditarGrafico.hide();
                                grid2.destroy();
                                createGrid();
                                //                            reloadGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                    }

                }
            },
            {
                text: 'Regresar',
                handler: function() {
                    windowEditarGrafico.hide();
                }
            }
        ]
    });

    /*****se crea la ventana de borrar un Grafico***/

    var msgGraficoBorrar = new Ext.form.TextField({
        fieldLabel:'Esta seguro que desea Eliminar este gr&aacute;fico?',
        labelSeparator:'',
        anchor:'90%',
        hidden : true
    });

    var nombreGraficoBorrar = new Ext.form.TextField({
        fieldLabel: '',
        name: 'nmGrafico',
        hidden :true
    });


    var borrarGraficoForm = new Ext.FormPanel({
        id:'borrarGraficoForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'reportesPrincipal/borrarGrafico.action' + '?cdReporte=' + codigoText.getValue(),
        items:[msgGraficoBorrar,nombreGraficoBorrar]
    });

    var windowGraficoBorrar = new Ext.Window({
        title: 'Eliminar Gr&aacute;fico',
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarGraficoForm,
        buttons: [
            {
                text: 'Eliminar',
                handler: function() {
                    if (borrarGraficoForm.form.isValid()) {
                        borrarGraficoForm.form.submit({
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error Eliminando Gr&aacute;fico', 'Ha ocurrido un error al eliminar el gr&aacute;fico');
                                windowGraficoBorrar.hide();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Gr&aacute;fico Eliminado', 'El gr&aacute;fico  ha sido eliminado satisfactoriamente');
                                windowGraficoBorrar.hide();
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
                    windowGraficoBorrar.hide();
                }
            }
        ]
    });


    /*********************
     *Se crea el grid de Planes
     ***********************/
    var aux;

    function createGrid() {
        var seleccion = true;
        Ext.PagingToolbar.prototype.onClick = function(which) {
            switch (which) {
                case "first":
                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta,cdReporte: codRep }});
                    break;
                case "prev":
                    desde = this.cursor - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta,cdReporte: codRep }});
                    //
                    break;
                case "next":
                    //
                    this.cursor = this.cursor + this.pageSize;
                    desde = this.cursor;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta,cdReporte: codRep }});
                    //
                    break;
                case "last":
                    //
                    desdeAux = store.getTotalCount() % this.pageSize;
                    desde = desdeAux ? (store.getTotalCount() - desdeAux) : store.getTotalCount() - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta,cdReporte: codRep }});
                    break;
                case "refresh":

                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta,cdReporte: codRep}});
                    grid2.destroy();
                    createGrid();

                    break;
            }
            ;
        };
        grid2 = new Ext.grid.GridPanel({
            id:'grid2',
            store:store,
            border:true,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            buttons:[
                {
                    text:'Agregar',
                    tooltip:'Agregar un nuevo Gr&aacute;fico',
                    handler:function() {
                        agregarGrafico();
                    }
                },
                {
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar gr&aacute;fico seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Seleccione', 'Seleccione el gr&aacute;fico a editar');


                        }
                    }
                },
                {
                    text:'Eliminar',
                    id:'borrar',
                    tooltip:'Eliminar el gr&aacute;fico seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Seleccione', 'Seleccione el Gr&aacute;fico a eliminar');
                        }
                    }
                },
                {
                    text:'Exportar',
                    tooltip:'Exportar Gr&aacute;ficos',
                    handler: function() {
                        showExportDialog(_ACTION_EXPORT + '?cdReporte=' + codigoText.getValue() + '&descripcion=Graficos')
                    }
                },
                {
                    text:'Regresar',
                    id:'regresar',
                    tooltip:'Regresa a la pantalla de Reportes',
                    handler: function () {
                        window.location.replace(_REGRESAR);
                    }
                }
            ],
            width:700,
            frame:true,
            height:290,
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado de Gr&aacute;ficos</span>',
            collapsible: true,
            renderTo:document.body,
            bbar: new Ext.PagingToolbar({
                pageSize:itemsPerPage,
                store: store,
                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
                emptyMsg: getLabelFromMap('400012', helpMap, 'No hay registros para visualizar')
            }),
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        seleccion = false;
                        selectedId = store.data.items[row].id;
                        nmReporte = store.data.items[row].data.nmGrafico;
                        Ext.getCmp('editar').on('click', function() {
                            //alert(store.data.items[row].data.descripcion);
                            tipoEditarGrafico.setValue(store.data.items[row].data.descripcion);
                            windowEditarGrafico.show();
                            Ext.getCmp('editarGrafform').getForm().loadRecord(rec);

                        });
                        Ext.getCmp('borrar').on('click', function() {

                            windowGraficoBorrar.show();
                            Ext.getCmp('borrarGraficoForm').getForm().loadRecord(rec);
                        });

                    }
                }

            }),
            viewConfig: {autoFill: true,forceFit:true}
        });
        store.load({params:{start:0,limit:20,cdReporte: codRep  }});

    }

    createGrid();
    //storeCombo.load({params:{start:0, limit:4,cdReporte: codRep}});
    reloadGrid();

    function reloadGrid() {
        var _params = {
            cdReporte: codRep
        };
        reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
    }

    function cbkReload(_r, _options, _success, _store) {
        if (!_success) {
            _store.removeAll();
            Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Aviso'), _store.reader.jsonData.actionErrors[0]);
        }
    }
});