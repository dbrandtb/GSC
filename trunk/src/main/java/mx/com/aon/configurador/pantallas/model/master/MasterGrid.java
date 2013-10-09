/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.master;

import java.io.Serializable;
import java.util.List;

import mx.com.aon.configurador.pantallas.model.components.ColumnGridEstandarVO;
import mx.com.aon.configurador.pantallas.model.components.RecordVO;
import mx.com.aon.configurador.pantallas.model.controls.ExtElement;

import org.apache.log4j.Logger;

/**
 * @author eflores
 * @date 17/11/2008
 *
 */
public class MasterGrid implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private final transient Logger logger = Logger.getLogger(MasterGrid.class);

    private List<ExtElement> extElements;
    
    private List<RecordVO> listRecords;
    
    private List<ColumnGridEstandarVO> listColumns;
    
//    private List<ButtonMaster> buttons;
    
    /**
     *
     */
    public MasterGrid() {
        super();
    }
    
    /**
     * @param extElements
     */
    public MasterGrid(List<ExtElement> extElements) {
        super();
        this.extElements = extElements;
    }

    /**
     * @return
     */
    public String getStoreGrid() {
        if (logger.isDebugEnabled()) {
            logger.debug("-> MasterGrid.getStoreGrid");
        }
        
        StringBuilder builder = new StringBuilder();
                
        builder.append("    function obtieneRegistro(){");
        builder.append("        url='flujocotizacion/agregaIncisosConsultaCotizacion.action';");
        builder.append("        store= new Ext.data.Store({");
        builder.append("            proxy: new Ext.data.HttpProxy({");
        builder.append("                url: url");
        builder.append("            }),");
        builder.append("            reader: new Ext.data.JsonReader({");
        builder.append("                root:'gridIncisos',");
        builder.append("                totalProperty:'totalCount'");
        builder.append("            },");
        builder.append(listRecords);
        builder.append("            ),");
        builder.append("            remoteSort:true");
        builder.append("            });");
        builder.append("            store.setDefaultSort('gridIncisos','desc');");
        builder.append("            store.load();");
        builder.append("            return store;");
        builder.append("    }  ");
        builder.append("    var store;");
        
        return builder.toString();
    }

    /**
     * @return
     */
    public String getBodyGrid() {
        if (logger.isDebugEnabled()) {
            logger.debug("-> MasterGrid.getBodyGrid");
        }
        
        StringBuilder builder = new StringBuilder();
        int i = 0;
        
        builder.append("    var cm = new Ext.grid.ColumnModel([");
        builder.append("        new Ext.grid.RowNumberer(),");
        for (ColumnGridEstandarVO columnGridEstandarVO : listColumns) {
            builder.append(columnGridEstandarVO);
            if (++i < listColumns.size()) {
                builder.append(",");
            }
        }
        builder.append("     ]);");
        builder.append("    var gridConf;");
        builder.append("    var selectedId;");
        builder.append("    function createGrid(){");
        builder.append("        gridConf = new Ext.grid.GridPanel({");
        builder.append("        store: obtieneRegistro(),");
        builder.append("        id:'lista-lista',");
        builder.append("        border:true,");
        builder.append("        baseCls:' background:white',");
        builder.append("        cm: cm,");
        builder.append("        buttons:");
//        builder.append(buttons);
        builder.append("               ,");
        
        /*builder.append("        buttons:[");
        builder.append("                {");
        builder.append("                text:'Agregar',");
        builder.append("                tooltip:'Agregar Elemento',");
        builder.append("                handler: function() {");
        builder.append("                    if (simple.form.isValid()) {");
        builder.append("                        simple.form.submit({");
        builder.append("                            url:'flujocotizacion/agregaIncisosConsultaCotizacion.action',");
        builder.append("                            waitMsg:'Procesando',");
        builder.append("                            failure: function(form, action) {");
        builder.append("                                Ext.MessageBox.alert('Estado','Error agregando elemento');");
        builder.append("                            },");
        builder.append("                            success: function(form, action) {");
        builder.append("                                Ext.MessageBox.alert('Elemento agregado exitósamente', action.result.info);");
        builder.append("                                store.load();");
        builder.append("                            }");
        builder.append("                        });");
        builder.append("                    } else{");
        builder.append("                        Ext.MessageBox.alert('Error', 'Complete todos los campos Requeridos');");
        builder.append("                    }");
        builder.append("                }");
        builder.append("                },{");
        builder.append("                text:'Detalle',");
        builder.append("                id:'detalle',");
        builder.append("                tooltip:'Detalle de los Elementos seleccionados'");
        builder.append("                },{");
        builder.append("                text:'Regresar',");
        builder.append("                id:'regresar',");
        builder.append("                tooltip:'Regresar a la pagina anterior'");
        builder.append("                },{");
        builder.append("                text:'Imprimir',");
        builder.append("                id:'imprimir',");
        builder.append("                tooltip:'Imprimir los Elementos seleccionados'");
        builder.append("                }");
        builder.append("                ],");*/
        builder.append("        width:600,");
        builder.append("        frame:true,");
        builder.append("        height:300,");
        builder.append("        buttonAlign:'left',");
        builder.append("        title:'<span style=\"color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;\">Listado</span>',");
        builder.append("        sm:new Ext.grid.RowSelectionModel({");
        builder.append("        singleSelect:true");
        builder.append("        }),");
        builder.append("    viewConfig: {autoFill: true,forceFit:true},");
        builder.append("    bbar: new Ext.PagingToolbar({");
        builder.append("        pageSize:25,");
        builder.append("        store: store,");
        builder.append("        displayInfo: true,");
        builder.append("        displayMsg: 'Mostrando registros {0} - {1} of {2}',");
        builder.append("        emptyMsg: \"No hay registros que mostrar\"");
        builder.append("        })");
        builder.append("        });");
        builder.append("        gridConf.render('gridConfig');");
        builder.append("    }");
        builder.append("    createGrid();");
        
        return builder.toString();
    }

    /**
     * @return the extElements
     */
    public List<ExtElement> getExtElements() {
        return extElements;
    }

    /**
     * @param extElements the extElements to set
     */
    public void setExtElements(List<ExtElement> extElements) {
        this.extElements = extElements;
    }

    /**
     * @return the listColumns
     */
    public List<ColumnGridEstandarVO> getListColumns() {
        return listColumns;
    }

    /**
     * @param listColumns the listColumns to set
     */
    public void setListColumns(List<ColumnGridEstandarVO> listColumns) {
        this.listColumns = listColumns;
    }

    /**
     * @return the listRecords
     */
    public List<RecordVO> getListRecords() {
        return listRecords;
    }

    /**
     * @param listRecords the listRecords to set
     */
    public void setListRecords(List<RecordVO> listRecords) {
        this.listRecords = listRecords;
    }

//    /**
//     * @return the buttons
//     */
//    public List<ButtonMaster> getButtons() {
//        return buttons;
//    }
//
//    /**
//     * @param buttons the buttons to set
//     */
//    public void setButtons(List<ButtonMaster> buttons) {
//        this.buttons = buttons;
//    }
}
