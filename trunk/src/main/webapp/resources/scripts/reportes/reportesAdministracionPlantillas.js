Ext.onReady(function() {    /////////////// EMPIEZA EL FUNCIONAMIENTO DE EXT
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = "side";
    var store;
    var url;

    var codPlantilla;
    var dsPlantilla;

    function cargarTabla() {
        //url=_BUSCAR_PLANTILLAS +'?dsPlantilla='+plantilla.getValue();       //Mapeo con struts
        url = _BUSCAR_PLANTILLAS;
        //        alert(store.getTotalCount());
        //        url="reportesPrincipal/obtenerListaPlantilla.action";
        store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _BUSCAR_PLANTILLAS
            }),
            reader: new Ext.data.JsonReader({
                root:'plantillas',
                totalProperty:'totalCount',
                successProperty : '@success',
                id: 'totales'
            }, [
                {name: 'cdPlantilla',  type: 'string',  mapping:'cdPlantilla'},
                {name: 'dsPlantilla',  type: 'string',  mapping:'dsPlantilla'},
                {name: 'status',  type: 'string',  mapping:'status'}


            ]),listeners: {
                load: function(store, response) {
                    if (store.getTotalCount() < 1) {
                        Ext.MessageBox.show({
                            title: 'Advertencia',
                            msg: 'No hay Datos Disponibles',
                            buttons: Ext.MessageBox.OK

                        });
                        plantilla.setValue(" ");
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


    var plantilla = new Ext.form.TextField({               // Campo de texto de plantilla en buscar
        fieldLabel: '<span style="color:#ffffff;font-size:14px;font-family:Arial,Helvetica,sans-serif;">4</span>Plantilla',
        name: 'dsPlantilla',
        maxLength :120,
        autoCreate : {
            tag : 'input',
            type : 'text',
            maxlength : 121
        },

        anchor : '98%'
    });

    var busquedaForm = new Ext.form.FormPanel({
        id:'reportes',
        title: '<span style="color:black;font-size:14px;">Administraci&oacute;n de Plantillas</span>',
        bodyStyle: 'background: white',
        buttonAlign: "bottom",
        labelAlign: 'right',
        renderTo: 'formBusqueda',
        frame: true,
        url: '',
        anchor:'98%',
        width:700,
        heigth: 150,
        items:[{
            title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
            bodyStyle: 'background: white',
            labelWidth: 75,
            layout: 'form',
            labelPad: 10,
            frame: false,
            baseCls: '',
            buttonAlign: "center",
            items: [
                plantilla
            ],
            buttons: [{
                text: 'Buscar',
                handler: function() {

                    if (grid2 != null) {
                        reloadGrid();

                    } else {
                        createGrid();
                    }

                }
            },{
                text:'Cancelar',
                handler: function() {
                    plantilla.reset();
                }
            }]
        }]
    });


    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header: "C&oacute;digo",  dataIndex:'cdPlantilla', width: 150, sortable:true},
        {header: "Descripci&oacute;n",  dataIndex:'dsPlantilla', width: 350, sortable:true},
        {header: "Status",     dataIndex:'status',    width: 160, sortable:true}

    ]);
    //cm.defaultSortable = true;

    /**
     *
     * Funcion agregar
     */
    var agregar = function() {

        var descripcion = new Ext.form.TextField({
            fieldLabel: 'Descripci&oacute;n',
            name: 'dsPlantilla',
            allowBlank: false,
            maxLength :120,
            width: 200,
            listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex ){
                    var cad = trim(descripcion.getValue());
                     if(cad == '')  {
                        descripcion.setValue('');
                    }
                }
            }
        });

        var status = new Ext.form.ComboBox({
            name:'status',
            fieldLabel: 'Status',
            labelAlign: 'right' ,
            editable:false,
            width: 200,
            store: new Ext.data.SimpleStore({
                fields: ['cveStatus', 'status'],
                data : [
                    // Elementos del combobox con los formatos
                    ['no', 'NO'],
                    ['si', 'SI']]
            }),
            displayField:'status',
            typeAhead: true,
            mode: 'local',
            allowBlank: false,
            forceSelection: true,
            triggerAction: 'all',
            emptyText:'Seleccione un Status...'
            //selectOnFocus:true
        });


        var mensajeForm = new Ext.form.FormPanel({
            id: 'mensajeform',
            labelAlign: 'right' ,
            baseCls: 'x-plain',
            columnWidth: 3,
            url:'reportesPrincipal/insertarPlantillas.action',
            items: [descripcion,status]
        });

        var insertaActualiPlanti = new Ext.Window({
            title: "Insertar Plantillas",
            layout: 'fit',
            plain: true,
            modal: true,
            closable:false,
            width: 350,
            heigth: 600,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            items: mensajeForm,
            buttons: [{
                text: 'Guardar',
                handler: function() {
                    if (mensajeForm.form.isValid()) {

                        mensajeForm.form.submit({
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Plantilla No Creada', action.result.wrongMsg);
                                insertaActualiPlanti.hide();
                                reloadGrid();
                                //store.load();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Plantilla Creada exitosamente', action.result.wrongMsg);
                                insertaActualiPlanti.hide();
                                reloadGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                    }
                }
            },{
                text: 'Regresar',
                handler: function() {
                    insertaActualiPlanti.hide();
                }
            }]
        });
        insertaActualiPlanti.show();
    };

    //Editar


    var codigoPlantilla = new Ext.form.NumberField({
        fieldLabel : '',
        hiddeParent:true,
        labelSeparator:'',
        name: 'cdPlantilla',
        allowBlank: false,
        hidden:true,
        width: 60
    });

    var descripcionPlantilla = new Ext.form.TextField({
        fieldLabel: 'Descripci&oacute;n',
        name: 'dsPlantilla',
        allowBlank: false,
        maxLength :120,
        width: 200,
        listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex ){
                    var cad = trim(descripcionPlantilla.getValue());
                     if(cad == '')  {
                        descripcionPlantilla.setValue('');
                    }
                }
            }
    });

    var statusPlantillaVista = new Ext.form.ComboBox({
        id:'statusEditarPlantilla',
        name:'status',
        fieldLabel: 'Status',
        labelAlign: 'right' ,
        // editable:true,
        width: 200,
        store: new Ext.data.SimpleStore({
            fields: ['cveStatus', 'status'],
            data : [
                // Elementos del combobox con los formatos
                ['no', 'NO'],
                ['si', 'SI']]
        }),
        displayField:'status',
        typeAhead: true,
        mode: 'local',
        allowBlank: false,
        forceSelection: true,
        triggerAction: 'all',
        emptyText:'Seleccione un Status...'
        //selectOnFocus:true
    });


    var editarForm = new Ext.form.FormPanel({
        id:'editForm',
        baseCls: 'x-plain',
        labelAlign: 'right' ,
        labelWidth: 75,
        url:'reportesPrincipal/editarPlantillas.action',
        items: [codigoPlantilla,descripcionPlantilla,statusPlantillaVista]
    });

    var windowEditar = new Ext.Window({
        title: 'Editar/Actualizar Plantilla',
        plain:true,
        closable:false,
        width: 330,
        height:150,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: editarForm,
        buttons: [{
            text: 'Guardar',
            handler: function() {

                if (editarForm.form.isValid()) {

                    editarForm.form.submit({
                        waitTitle:'Espere',
                        waitMsg:'Procesando...',
                        params:                //Prametros al action
                        {
                            statusPlantilla: Ext.getCmp('statusEditarPlantilla').getValue()
                        },

                        failure: function(form, action) {
                            Ext.MessageBox.alert('Plantilla No Editada', action.result.wrongMsg);
                            windowEditar.hide();
                            /*grid2.destroy();
                             createGrid();*/
                            reloadGrid();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('La plantilla fue editada correctamente', action.result.wrongMsg);
                            windowEditar.hide();
                            grid2.destroy();
                            createGrid();
                            //reloadGrid();
                        }
                    });
                } else {
                    Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                }

            }

        },{
            text: 'Regresar',
            handler: function() {
                // editarForm.reset();
                //  windowEditar.reset();
                /* codigoPlantilla.reset();
                 descripcionPlantilla.reset();
                 statusPlantillaVista.reset();
                 statusPlantilla.reset();*/
                windowEditar.hide();


            }
        }]
    });
    // BORRAR

    var cdPlantilla = new Ext.form.NumberField({
        fieldLabel: '',
        name:'cdPlantilla',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar esta Plantilla?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });

    var borrarForm = new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'reportesPrincipal/borrarPlantillas.action',
        items:[ msgBorrar, cdPlantilla]
    });

    var windowBorrar = new Ext.Window({
        title: 'Eliminar Plantilla',
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
        buttons: [{
            text: 'Eliminar',
            handler: function() {
                if (borrarForm.form.isValid()) {
                    borrarForm.form.submit({
                        waitTitle:'Espere',
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            /*if (action.result.validarAux == 100262)
                                Ext.MessageBox.alert('Error Eliminando Plantilla', 'No puede borrar una plantilla que tenga productos/atributos asociados');
                            else*/
                            Ext.MessageBox.alert('Error Eliminando Plantilla', action.result.wrongMsg);
                            windowBorrar.hide();
                            grid2.destroy();
                            createGrid();
                            //reloadGrid();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Plantilla Eliminado', action.result.wrongMsg);
                            windowBorrar.hide();
                            grid2.destroy();
                            createGrid();
                            //reloadGrid();
                        }
                    });
                } else {
                    Ext.MessageBox.alert('Error', 'Verifique!');
                }
            }
        },{
            text: 'Cancelar',
            handler: function() {
                windowBorrar.hide();
            }
        }]
    });
    /*****/


    /**
     *
     * Funcion Reporte
     */
    var reporte = function() {

        var plantilla = new Ext.form.TextField({
            fieldLabel: 'Plantilla',
            name: 'cdPlantillaA',
            width: 70
        });

        var plantilla1 = new Ext.form.TextField({
            name: 'dsPlantillaA',
            width: 200
        });

        var corporativo = new Ext.form.ComboBox({
            fieldLabel: 'Corporativo',
            //           name: 'dsCorporativo',
            labelAlign: 'right' ,
            width: 150
        });

        var aseguradora = new Ext.form.ComboBox({
            fieldLabel: 'Aseguradora',
            labelAlign: 'right' ,
            width: 150
        });
        var producto = new Ext.form.ComboBox({
            fieldLabel: 'Producto',
            labelAlign: 'right' ,
            width: 150
        });

        var reporte = new Ext.form.ComboBox({
            fieldLabel: 'Reporte',
            labelAlign: 'right' ,
            width: 150
        });

        var genera = new Ext.form.Checkbox({
            fieldLabel: 'Genera Gr&aacute;fico',
            width: 50
        });

        var mensajeForm = new Ext.form.FormPanel({
            id: 'mensajeform',
            labelAlign: 'right' ,
            baseCls: 'x-plain',
            columnWidth: 2,
            url:'reportesPrincipal/agregarPlantillasReportes.action',
            items: [plantilla,plantilla1,corporativo,aseguradora,producto,reporte,genera]
        });

        var AsociarPlantilla = new Ext.Window({
            title: "Asociar Plantilla a Reporte",
            layout: 'fit',
            plain: true,
            modal: true,
            closable:false,
            minWidth: 200,
            minHeigth: 400,
            width: 400,
            heigth: 600,
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            items: mensajeForm,
            buttons: [{
                text: 'Guardar',
                handler: function() {
                    if (mensajeForm.form.isValid()) {
                        mensajeForm.form.submit({
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Plantilla Creado', action.result.errorInfo);
                                AsociarPlantilla.hide();
                                grid2.destroy();
                                createGrid();
                                //store.load();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Plantilla Creado exitosamente', 'Se ha ingresado una nueva plantilla');
                                AsociarPlantilla.hide();
                                grid2.destroy();
                                createGrid();
                                store.load();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                    }
                }
            },{
                text: 'Regresar',
                handler: function() {
                    AsociarPlantilla.hide();
                }
            }]
        });
        AsociarPlantilla.show();
    };

    var grid2;
    var selectedId;
    var sm;

    function createGrid() {
        var seleccion = true;
        var desde;
        var hasta;
        var desdeAux;

        Ext.PagingToolbar.prototype.onClick = function(which) {
            switch (which) {
                case "first":
                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{
                        start:desde,limit:hasta,
                        dsPlantilla:plantilla.getValue()}});
                    break;
                case "prev":
                    desde = this.cursor - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsPlantilla:plantilla.getValue()}});
                //
                    break;
                case "next":
                //
                    this.cursor = this.cursor + this.pageSize;
                    desde = this.cursor;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsPlantilla:plantilla.getValue()}});
                //
                    break;
                case "last":
                    desdeAux = store.getTotalCount() % this.pageSize;
                    desde = desdeAux ? (store.getTotalCount() - desdeAux) : store.getTotalCount() - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde, limit: hasta,
                        dsPlantilla:plantilla.getValue()}});
                    break;
                case "refresh":

                    desde = 0;
                    hasta = this.pageSize;
                   store.load({params:{start:desde, limit:hasta}});
                    plantilla.reset();
                    grid2.destroy();
                    createGrid();
                    break;
            }
            ;
        };

        grid2 = new Ext.grid.GridPanel({
            id:grid2,
            border:true,
            store: cargarTabla(),
            //baseCls:' background:white ',
            cm: cm,
            buttonAlign: 'center',
            width:700,
            height:300,
            frame:true,
            collapsible: true,
            //            enableColLock:false,
            stripeRows: true,
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
            renderTo:'gridElementos',
            bbar: new Ext.PagingToolbar({
                //                itemsPerPage:itemsPerPage,
                pageSize:itemsPerPage,
                store: store,
                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
                emptyMsg: getLabelFromMap('400012', helpMap, 'No hay registros para visualizar')
            }),
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        seleccion = false;
                        selectedId = store.data.items[row].id;

                        codPlantilla = store.data.items[row].data.cdPlantilla;
                        dsPlantilla = store.data.items[row].data.dsPlantilla;

                        Ext.getCmp('editar').on('click', function() {
                            windowEditar.show();
                            Ext.getCmp('editForm').getForm().loadRecord(rec);
                        });

                        Ext.getCmp('borrar').on('click', function() {
                            windowBorrar.show();
                            Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                        });

                        Ext.getCmp('atributos').on('click', function() {

                            window.location.replace('reportesPrincipal/reportesAdministracionPlantillasAtributos.action?dsPlantilla=' + dsPlantilla + '&cdPlantilla=' + codPlantilla);

                        });

                        Ext.getCmp('productos').on('click', function() {
                            window.location.replace('reportesPrincipal/desplegarProductosPlantillas.action?dsPlantilla=' + dsPlantilla + '&cdPlantilla=' + codPlantilla);

                        });
                    }
                }

            }),
            buttons:[
                {text:'Agregar',
                    tooltip:'Agregar una nueva plantilla',
                    handler:function() {
                        agregar();
                    }
                },{
                    text:'Editar',
                    id:'editar',
                    tooltip:'Editar la plantilla seleccionada',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione la plantilla a editar');
                        }
                    }
                },{
                    text:'Eliminar',
                    id:'borrar',
                    tooltip:'Eliminar la plantilla seleccionada',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione la plantilla a eliminar');
                        }
                    }
                }  ,{
                    text: 'Atributos',
                    id:'atributos',
                    tooltip: 'Atributos de la Plantilla seleccionada',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione la plantilla para ver sus atributos');
                        }
                    }
                },{
                    id:'productos',
                    text:'Productos',
                    tooltip:'Productos de la Plantilla seleccionada',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Error', 'Seleccione la plantilla para ver sus productos');
                        }
                    }
                },{
                    text:'Exportar',
                    tooltip:'Exportar Plantillas',
                    handler: function() {
                        showExportDialog(_ACTION_EXPORT + '?dsPlantilla=' + plantilla.getValue() + '&descripcion=GraficosPlantilllas');
                    }
                }
            ]


        });
        grid2.render();
        //        alert(this.pageSize);
        /*store.load({params:{start:0, limit:100}});*/
        //    alert(store.getTotalCount());
    }
    ;
    busquedaForm.render();
    createGrid();

    function reloadGrid() {
     
        var _storeb = grid2.store;
        _storeb.baseParams = grid2.baseParams || {};

        _storeb.reload({
            params: {start:0,limit:100,dsPlantilla:plantilla.getValue()},
            callback: function (r, options, success) {
                if (!success) {
                    _storeb.removeAll();
                }
            }
        });
    }
    ;

});