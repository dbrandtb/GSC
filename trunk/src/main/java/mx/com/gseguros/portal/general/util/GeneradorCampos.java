/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.util.List;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;

/**
 *
 * @author Jair
 */
public class GeneradorCampos {
    
    public static final String idPrefix="idAutoGenerado";
    public static final String namePrefix="parametros.pv_otvalor";
    private org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(GeneradorCampos.class);
    private Item items;
    private Item fields;
    
    public void genera(List<Tatri> lt) throws Exception
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
    
    public void generaCampoYField(List<Tatri> lt, Tatri ta, Integer idx) throws Exception
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
            if(ta.getType().equals(Tatri.TATRISIT))
            {
            	proxy.add("url",UrlConstantes.OBTENER_ATRIBUTOS);
            }
            else if(ta.getType().equals(Tatri.TATRIPOL))
            {
            	proxy.add("url",UrlConstantes.OBTENER_ATRIBUTOS_POL);
            }
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
            if(idx<lt.size()-1&&lt.get(idx+1).getCdtablj1()!=null&&!lt.get(idx+1).getCdtablj1().isEmpty())//para el padre anidado
            {
            	this.agregarHerencia(lt,it,idx);
            }	
            if(idx>0&&ta.getCdtablj1()!=null&&!ta.getCdtablj1().isEmpty())//para el hijo anidado
            {
                it.add(Item.crear("forceSelection",false));
                it.add(Item.crear("heredar",
                		 "function(remoto){"
                		+"    if(!this.noEsPrimera||remoto==true)"
                		+"    {"
                		+"        this.noEsPrimera=true;"
                		+"        this.getStore().load({"
                		+"            params:{"
                		+"		          idPadre:Ext.getCmp('"+this.idPrefix+(idx-1)+"').getValue()"
                		+"    		  },"
                		+"		      callback:function(){"
                		+"			      var thisCmp=Ext.getCmp('"+this.idPrefix+idx+"');"
                        +"			      var valorActual=thisCmp.getValue();"
                        +"			      var dentro=false;"
                        +"			      thisCmp.getStore().each(function(record){"
                        +"				      if(valorActual==record.get('key'))dentro=true;"
                        +"			      });"
                        +"			      if(!dentro)thisCmp.clearValue();"
                		+"		      }"
                		+"	      });"
                		+"    }"
                		+"},"
                		+"listeners:{"
                		+"	change:{fn:function(){this.heredar();}}"
                		+"}").setQuotes(""));
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
            if(ta.getNmlmin()!=null)
            {
            	it.add(Item.crear("minLength", Integer.parseInt(ta.getNmlmin())));
            }
            if(ta.getNmlmax()!=null)
            {
            	it.add(Item.crear("maxLength", Integer.parseInt(ta.getNmlmax())));
            }
            if(idx<lt.size()-1&&lt.get(idx+1).getCdtablj1()!=null&&!lt.get(idx+1).getCdtablj1().isEmpty())
            {
            	this.agregarHerencia(lt,it,idx);
            }
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
            if(ta.getNmlmin()!=null)
            {
            	it.add(Item.crear("minLength", Integer.parseInt(ta.getNmlmin())));
            }
            if(ta.getNmlmax()!=null)
            {
            	it.add(Item.crear("maxLength", Integer.parseInt(ta.getNmlmax())));
            }
            if(idx<lt.size()-1&&lt.get(idx+1).getCdtablj1()!=null&&!lt.get(idx+1).getCdtablj1().isEmpty())
            {
            	this.agregarHerencia(lt,it,idx);
            }
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

    private void agregarHerencia(List<Tatri> lt, Item it, Integer idx) throws Exception {//para el padre anidado
    	it.add(Item.crear("listeners","" +
    			"{" +
    			"    blur:{" +
    			"        fn:function(){Ext.getCmp('"+this.idPrefix+(idx+1)+"').heredar(true);}" +
    			"    }" +
    			"}")
    			.setQuotes(""));
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
