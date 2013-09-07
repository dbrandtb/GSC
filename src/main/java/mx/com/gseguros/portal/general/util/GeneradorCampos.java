/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.util.List;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatrisit;

/**
 *
 * @author Jair
 */
public class GeneradorCampos {
    
    public static final String idPrefix="idAutoGenerado";
    public static final String namePrefix="parametros.otval";
    private org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(GeneradorCampos.class);
    private Item items;
    private Item fields;
    
    public void genera(List<Tatrisit> lt) throws Exception
    {
        items=new Item("items",null,Item.ARR);
        fields=new Item("fields",null,Item.ARR);
        if(lt!=null&&!lt.isEmpty())
        {
            for(int i=0;i<lt.size();i++)
            {
                this.generaCampoYField(lt, lt.get(i), i);
            }
        }
        log.debug(fields.toString());
        log.debug(items.toString());
    }
    
    public void generaCampoYField(List<Tatrisit> lt, Tatrisit ta, Integer idx) throws Exception
    {
        Item fi=new Item();
        fi.setType(Item.OBJ);
        fi.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
        String type="string";
        if(ta.getSwformat().equals("A"))
            type="string";
        else if(ta.getSwformat().equals("N")||ta.getSwformat().equals("P"))
            type="numeric";
        else if(ta.getSwformat().equals("F"))
            type="date";
        //fi.add(Item.crear("type", type));
        //fi.add(Item.crear("type", "string"));
        
        Item it=new Item();
        if(ta.getOttabval()!=null&&!ta.getOttabval().isEmpty())
        //se alimenta de la base de datos
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.ComboBox',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()!=null&&ta.getSwobliga().equals("N")));
            it.add(Item.crear("fieldLabel",ta.getDsatribu()));
            it.add(Item.crear("style","margin:5px"));
            it.add(Item.crear("forceSelection",true));
            it.add(Item.crear("typeAhead",true));
            it.add(Item.crear("matchFieldWidth",true));
            Item store=new Item(null,null,Item.OBJ,"store:Ext.create('Ext.data.Store',{","})");
            it.add(store);
            store.add("model","Generic");
            store.add("autoLoad",true);
            Item proxy=new Item("proxy",null,Item.OBJ);
            store.add(proxy);
            proxy.add("type","ajax");
            proxy.add("url",UrlConstantes.OBTENER_ATRIBUTOS);
            proxy.add(
                    Item.crear("extraParams", null, Item.OBJ)
                    .add("cdatribu",ta.getCdatribu())
                    );
            proxy.add(
                    Item.crear("reader", null, Item.OBJ)
                    .add("type","json")
                    .add("root","lista")
                    );
            it.add(Item.crear("queryMode", "local"));
            it.add(Item.crear("displayField", "value"));
            it.add(Item.crear("valueField", "key"));
            it.add(Item.crear("editable", "false"));
            //it.add(Item.crear("emptyText", "Seleccione..."));
            if(idx>0&&ta.getCdtablj1()!=null&&!ta.getCdtablj1().isEmpty())
            {
                it.add(Item.crear("forceSelection",false));
                Item heredar=new Item(null,null,Item.OBJ,"heredar:function(){","},"
                    + "listeners:{"
                    + "focus:{fn:function(){this.heredar();}},"
                    + "change:{fn:function(){this.heredar();}}"
                    + "}");
                it.add(heredar);
                Item load=new Item(null,null,Item.OBJ,"this.getStore().load({","})");
                heredar.add(load);
                load.add(
                        Item.crear("params",null,Item.OBJ)
                        .add(null,null,Item.OBJ,"{idPadre:Ext.getCmp('"+this.idPrefix+(idx-1)+"').getValue(",")}")
                        );
                Item callback=Item.crear(null, null, Item.OBJ, "callback:function(){"
                        + "var thisCmp=Ext.getCmp('"+this.idPrefix+idx+"');"
                        + "var valorActual=thisCmp.getValue();"
                        + "var dentro=false;"
                        + "thisCmp.getStore().each(function(record){"
                        + "if(valorActual==record.get('key'))dentro=true;"
                        + "});"
                        + "if(!dentro)thisCmp.clearValue();", "}");
                load.add(callback);
                /*Item listen=new Item("listeners",null,Item.OBJ);
                it.add(listen);
                listen.add(Item.crear(null,null,Item.OBJ,"focus:heredar",""));
                listen.add(Item.crear(null,null,Item.OBJ,"change:heredar",""));*/
            }
        }
        else if(ta.getSwformat().equals("A"))
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.TextField',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()!=null&&ta.getSwobliga().equals("N")));
            it.add(Item.crear("fieldLabel",ta.getDsatribu()));
            it.add(Item.crear("style","margin:5px"));
            //it.add(Item.crear("emptyText", "Introduzca..."));
            it.add(Item.crear("minLength", Integer.parseInt(ta.getNmlmin())));
            it.add(Item.crear("maxLength", Integer.parseInt(ta.getNmlmax())));
        }
        else if(ta.getSwformat().equals("N")||ta.getSwformat().equals("P"))
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.NumberField',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()!=null&&ta.getSwobliga().equals("N")));
            it.add(Item.crear("fieldLabel",ta.getDsatribu()));
            it.add(Item.crear("style","margin:5px"));
            //it.add(Item.crear("emptyText", "Introduzca..."));
            it.add(Item.crear("minLength", Integer.parseInt(ta.getNmlmin())));
            it.add(Item.crear("maxLength", Integer.parseInt(ta.getNmlmax())));
        }
        else if(ta.getSwformat().equals("F"))
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.DateField',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()!=null&&ta.getSwobliga().equals("N")));
            it.add(Item.crear("fieldLabel",ta.getDsatribu()));
            it.add(Item.crear("style","margin:5px"));
            it.add(Item.crear("format","d/m/Y"));
        }
        items.add(it);
        fields.add(fi);
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }

    public Item getFields() {
        return fields;
    }

    public void setFields(Item fields) {
        this.fields = fields;
    }
    
}
