/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;

/**
 *
 * @author Jair
 */
public class GeneradorCampos {
    
    public String idPrefix="idAutoGenerado";
    public static final String namePrefix="parametros.pv_otvalor";
    private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(GeneradorCampos.class);
    private Item items;
    private Item fields;
    private Item columns;
    private String context;
    private String cdgarant;
    private String cdramo;
    private String cdrol;
    private String cdtipsit;
    
    private boolean parcial=false;
    
    public GeneradorCampos(String context)
    {
    	this.context="/"+context;
    	log.debug("contexto para el generador de campos: "+this.context);
    	idPrefix+="_"+System.currentTimeMillis()+"_"+((long)Math.ceil((Math.random()*10000d)))+"_";
    }
    
    public void generaParcial(List<Tatri> lt) throws Exception
    {
        this.parcial=true;
        this.genera(lt);
    }
    
    public void genera(List<Tatri> lt) throws Exception
    {
        items=new Item(parcial?null:"items",null,Item.ARR);
        fields=new Item(parcial?null:"fields",null,Item.ARR);
        columns=new Item(null,null,Item.ARR);
        if(lt!=null&&!lt.isEmpty())
        {
            for(int i=0;i<lt.size();i++)
            {
                this.generaCampoYFieldYColumn(lt, lt.get(i), i);
            }
        }
        log.debug(fields.toString());
        log.debug(items.toString());
        log.debug(columns.toString());
    }
    
    public void generaCampoYFieldYColumn(List<Tatri> lt, Tatri ta, Integer idx) throws Exception
    {
    	boolean listeners=false;
        Item fi=new Item();
        fi.setType(Item.OBJ);
        if(!ta.getType().equalsIgnoreCase(Tatri.TATRIGEN))
        {            	
        	fi.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
        }
        else
        {
        	fi.add(Item.crear("name", ta.getMapa().get("OTVALOR10")));
        }
        String type="string";
        if(ta.getSwformat().equals("A")||StringUtils.isNotBlank(ta.getOttabval()))//si es combo solo pone strings
            type="string";
        else if(ta.getSwformat().equals("N"))
            type="int";
        else if(ta.getSwformat().equals("P"))
        	type="float";
        else if(ta.getSwformat().equals("F"))
            type="date";
        fi.add(Item.crear("type", type));
        if(ta.getSwformat().equals("F"))
        	fi.add(Item.crear("dateFormat", "d/m/Y"));
        
        Item col=new Item();
        col.setType(Item.OBJ);
        if(!ta.getType().equalsIgnoreCase(Tatri.TATRIGEN))
        {            	
        	col.add("dataIndex",this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu()));
        }
        else
        {
        	col.add("dataIndex",ta.getMapa().get("OTVALOR10"));
        }
        col.add("header",ta.getDsatribu());
        col.add("flex",1);
        if(type.equals("date"))
        {
        	col.add("xtype"  , "datecolumn");
        	col.add("format" , "d/m/Y");
        }
        if(ta.getType()==Tatri.TATRIGEN)
        {
        	String visible=ta.getMapa().get("OTVALOR08");
        	if(visible.equalsIgnoreCase("N"))
        	{
        		col=null;
        	}
        	else if(visible.equalsIgnoreCase("H"))
        	{
        		col.add("hidden",true);
        	}
        	
        	if(col!=null)
        	{
	        	String renderer=ta.getMapa().get("OTVALOR09");
	        	if(renderer==null||renderer.length()==0||renderer.equalsIgnoreCase("N"))
	        	{
	        		//sin renderer
	        	}
	        	else if(renderer.equalsIgnoreCase("MONEY"))
	        	{
	        		col.add(Item.crear("renderer","Ext.util.Format.usMoney").setQuotes(""));
	        	}
	        	else if(renderer.length()>0)
	        	{
	        		col.add(Item.crear("renderer",renderer).setQuotes(""));
	        	}
        	}
        }
        
        Item it=new Item();
        if(StringUtils.isNotBlank(ta.getOttabval()))
        //se alimenta de la base de datos
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.ComboBox',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("readOnly", ta.isReadOnly()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()==null||!ta.getSwobliga().equalsIgnoreCase("S")));
            it.add(Item.crear("fieldLabel",ta.getDsatribu()));
            it.add(Item.crear("style","margin:5px"));
            it.add(Item.crear("forceSelection",false));
            it.add(Item.crear("typeAhead",true));
            it.add(Item.crear("matchFieldWidth",true));
            it.add(Item.crear("queryMode", "local"));
            Item store=new Item(null,null,Item.OBJ,"store:Ext.create('Ext.data.Store',{","})");
            it.add(store);
            store.add("model","Generic");
            store.add("autoLoad",true);//debe ser true!
            Item proxy=new Item("proxy",null,Item.OBJ);
            store.add(proxy);
            proxy.add("type","ajax");
            proxy.add("url",this.context+"/catalogos/obtieneCatalogo.action");
            if(ta.getType().equals(Tatri.TATRISIT))
            {
            	proxy.add(
                        Item.crear("extraParams", null, Item.OBJ)
                        .add("'params.cdatribu'",ta.getCdatribu())
                        .add("'params.cdtipsit'",cdtipsit)
                        .add("catalogo",Catalogos.TATRISIT.getCdTabla())
                        );
            }
            else if(ta.getType().equals(Tatri.TATRIPOL))
            {
            	proxy.add(
                        Item.crear("extraParams", null, Item.OBJ)
                        .add("'params.cdatribu'",ta.getCdatribu())
                        .add("catalogo",Catalogos.TATRIPOL.getCdTabla())
                        );
            }
            else if(ta.getType().equals(Tatri.TATRIGAR))
            {
            	proxy.add(
                        Item.crear("extraParams", null, Item.OBJ)
                        .add("'params.cdatribu'",ta.getCdatribu())
                        .add("'params.cdgarant'",cdgarant)
                        .add("catalogo",Catalogos.TATRIGAR.getCdTabla())
                        );
            }
            else if(ta.getType().equals(Tatri.TATRIPER))
            {
            	proxy.add(
                        Item.crear("extraParams", null, Item.OBJ)
                        .add("'params.cdramo'"  ,cdramo)
                        .add("'params.cdrol'"   ,cdrol)
                        .add("'params.cdatribu'",ta.getCdatribu())
                        .add("'params.cdtipsit'",cdtipsit)
                        .add("catalogo",Catalogos.TATRIPER.getCdTabla())
                        );
            }
            else if(ta.getType().equals(Tatri.TATRIGEN))
            {
            	//////////////////////////////////////////////////////
            	////// del 31 al 50 son parametros para lectura //////
            	////// 31:llave 32:valor...                     //////
            	Item extraParams=Item.crear("extraParams", null, Item.OBJ)
                        .add("catalogo",ta.getMapa().get("OTVALOR03"));
            	for(int i=31;i<=49;i+=2)
            	{
            		if(ta.getMapa().get("OTVALOR"+i)!=null&&ta.getMapa().get("OTVALOR"+i).length()>0)
            		{
            			extraParams.add(
            					Item.crear(ta.getMapa().get("OTVALOR"+i),ta.getMapa().get("OTVALOR"+(i+1)))
            					.setQuotes("")
            					);
            		}
            	}
            	proxy.add(extraParams);
            	////// del 31 al 50 son parametros para lectura //////
            	//////////////////////////////////////////////////////
            	
            	//////////////////////////////////////////////
            	////// cuando el combo es autocompleter //////
            	////// otvalor12 = queryParam           //////
            	if(ta.getMapa().get("OTVALOR12")!=null&&ta.getMapa().get("OTVALOR12").length()>0)
            	{
            		it.add(Item.crear("hideTrigger" , true));
            		it.add(Item.crear("minChars"    , 3));
            		it.add(Item.crear("queryMode"   , "remote"));
            		it.add(Item.crear("queryParam"  , ta.getMapa().get("OTVALOR12")));
            		store.add("autoLoad",false);
            	}
            	////// cuando el combo es autocompleter //////
            	//////////////////////////////////////////////
            	
            	////////////////////////////////
            	////// para valor inicial //////
            	////// otvalor13          //////
            	if(ta.getMapa().get("OTVALOR13")!=null&&ta.getMapa().get("OTVALOR13").length()>0)
            	{
            		it.add(Item.crear("value" , ta.getMapa().get("OTVALOR13")).setQuotes(""));
            	}
            	////// para valor inicial //////
            	////////////////////////////////
            	
            	/////////////////////////////////////
            	////// para saber si es hidden //////
            	if(ta.getMapa().get("OTVALOR14")!=null&&ta.getMapa().get("OTVALOR14").equalsIgnoreCase("S"))
            	{
            		it.add(Item.crear("hidden" , true));
            	}
            	////// para saber si es hidden //////
            	/////////////////////////////////////
            	
            	/////////////////////////////////////
            	////// para saber si es hidden //////
            	if(ta.getMapa().get("OTVALOR14")!=null&&ta.getMapa().get("OTVALOR14").equalsIgnoreCase("S"))
            	{
            		it.add(Item.crear("hidden" , true));
            	}
            	////// para saber si es hidden //////
            	/////////////////////////////////////
            }
            if(!ta.getType().equalsIgnoreCase(Tatri.TATRIGEN))
            {
            	it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            }
            else
            {
            	it.add(Item.crear("name", ta.getMapa().get("OTVALOR10")));
            }
            proxy.add(
                    Item.crear("reader", null, Item.OBJ)
                    .add("type","json")
                    .add("root","lista")
                    );
            it.add(Item.crear("displayField", "value"));
            it.add(Item.crear("valueField", "key"));
            it.add(Item.crear("editable", "false"));
            //it.add(Item.crear("emptyText", "Seleccione..."));
            if(idx<lt.size()-1&&StringUtils.isNotBlank(lt.get(idx+1).getCdtablj1()))//para el padre anidado
            {
            	if(idx>0&&StringUtils.isNotBlank(ta.getCdtablj1()))//es padre e hijo a la vez
            	{
            		this.agregarHerencia2(lt,it,idx);
            	}
            	else
            	{
            		this.agregarHerencia(lt,it,idx);
            	}
            	listeners=true;
            }	
            if(idx>0&&StringUtils.isNotBlank(ta.getCdtablj1()))//para el hijo anidado
            {
                it.add(Item.crear("forceSelection",false));
                it.add(Item.crear("heredar",
                		 "function(remoto){"
                		+"    debug('heredar');"
                		+"    if(!this.noEsPrimera||remoto==true)"
                		+"    {"
                		+"        debug('no es primera o es remoto');"
                		+"        this.noEsPrimera=true;"
                		+"        this.getStore().load({"
                		+"            params:{"
                		+"		          'params.idPadre':Ext.getCmp('"+this.idPrefix+(idx-1)+"').getValue()"
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
                		+"}"
                		+(listeners?"":","
                		+"listeners:{"
                		+"	change:{fn:function(){this.heredar();}}"
                		+"}")).setQuotes(""));
            }
        }
        else if(ta.getSwformat().equals("A"))
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.TextField',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("readOnly", ta.isReadOnly()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()==null||!ta.getSwobliga().equalsIgnoreCase("S")));
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
            if(idx<lt.size()-1&&StringUtils.isNotBlank(lt.get(idx+1).getCdtablj1()))
            {
            	this.agregarHerencia(lt,it,idx);
            }
            if(!ta.getType().equalsIgnoreCase(Tatri.TATRIGEN))
            {
            	it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            }
            else
            {
            	it.add(Item.crear("name", ta.getMapa().get("OTVALOR10")));
            	
            	////////////////////////////////
            	////// para valor inicial //////
            	////// otvalor13          //////
            	if(ta.getMapa().get("OTVALOR13")!=null&&ta.getMapa().get("OTVALOR13").length()>0)
            	{
            		it.add(Item.crear("value" , ta.getMapa().get("OTVALOR13")).setQuotes(""));
            	}
            	////// para valor inicial //////
            	////////////////////////////////
            	
            	/////////////////////////////////////
            	////// para saber si es hidden //////
            	if(ta.getMapa().get("OTVALOR14")!=null&&ta.getMapa().get("OTVALOR14").equalsIgnoreCase("S"))
            	{
            		it.add(Item.crear("hidden" , true));
            	}
            	////// para saber si es hidden //////
            	/////////////////////////////////////
            }
        }
        else if(ta.getSwformat().equals("N")||ta.getSwformat().equals("P"))
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.NumberField',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("readOnly", ta.isReadOnly()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()==null||!ta.getSwobliga().equalsIgnoreCase("S")));
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
            if(idx<lt.size()-1&&StringUtils.isNotBlank(lt.get(idx+1).getCdtablj1()))
            {
            	this.agregarHerencia(lt,it,idx);
            }
            if(!ta.getType().equalsIgnoreCase(Tatri.TATRIGEN))
            {
            	it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            }
            else
            {
            	it.add(Item.crear("name", ta.getMapa().get("OTVALOR10")));
            	
            	////////////////////////////////
            	////// para valor inicial //////
            	////// otvalor13          //////
            	if(ta.getMapa().get("OTVALOR13")!=null&&ta.getMapa().get("OTVALOR13").length()>0)
            	{
            		it.add(Item.crear("value" , ta.getMapa().get("OTVALOR13")).setQuotes(""));
            	}
            	////// para valor inicial //////
            	////////////////////////////////
            	
            	/////////////////////////////////////
            	////// para saber si es hidden //////
            	if(ta.getMapa().get("OTVALOR14")!=null&&ta.getMapa().get("OTVALOR14").equalsIgnoreCase("S"))
            	{
            		it.add(Item.crear("hidden" , true));
            	}
            	////// para saber si es hidden //////
            	/////////////////////////////////////
            }
        }
        else if(ta.getSwformat().equals("F"))
        {
            it.setType(Item.OBJ);
            it.setComposedName("Ext.create('Ext.form.DateField',{");
            it.setComposedNameClose("})");
            it.add(Item.crear("id", this.idPrefix+idx));
            it.add(Item.crear("cdatribu", ta.getCdatribu()));
            it.add(Item.crear("readOnly", ta.isReadOnly()));
            it.add(Item.crear("allowBlank",ta.getSwobliga()==null||!ta.getSwobliga().equalsIgnoreCase("S")));
            it.add(Item.crear("fieldLabel",ta.getDsatribu()));
            it.add(Item.crear("style","margin:5px"));
            it.add(Item.crear("format","d/m/Y"));
            if(!ta.getType().equalsIgnoreCase(Tatri.TATRIGEN))
            {
            	it.add(Item.crear("name", this.namePrefix+(ta.getCdatribu().length()>1?ta.getCdatribu():"0"+ta.getCdatribu())));
            }
            else
            {
            	it.add(Item.crear("name", ta.getMapa().get("OTVALOR10")));
            	
            	////////////////////////////////
            	////// para valor inicial //////
            	////// otvalor13          //////
            	if(ta.getMapa().get("OTVALOR13")!=null&&ta.getMapa().get("OTVALOR13").length()>0)
            	{
            		it.add(Item.crear("value" , ta.getMapa().get("OTVALOR13")).setQuotes(""));
            	}
            	////// para valor inicial //////
            	////////////////////////////////
            	
            	/////////////////////////////////////
            	////// para saber si es hidden //////
            	if(ta.getMapa().get("OTVALOR14")!=null&&ta.getMapa().get("OTVALOR14").equalsIgnoreCase("S"))
            	{
            		it.add(Item.crear("hidden" , true));
            	}
            	////// para saber si es hidden //////
            	/////////////////////////////////////
            }
        }
        items.add(it);
        fields.add(fi);
        if(col!=null)
        {
        	columns.add(col);
        }
    }

    private void agregarHerencia(List<Tatri> lt, Item it, Integer idx) throws Exception {//para el padre anidado
    	it.add(Item.crear("listeners","" +
    			"{" +
    			"    blur:{" +
    			"        fn:function(){debug('blur');Ext.getCmp('"+this.idPrefix+(idx+1)+"').heredar(true);}" +
    			"    }" +
    			"}")
    			.setQuotes(""));
	}
    
    private void agregarHerencia2(List<Tatri> lt, Item it, Integer idx) throws Exception {//para el padre anidado
    	it.add(Item.crear("listeners","" +
    			"{" +
    			"    blur:{" +
    			"        fn:function(){debug('blur');Ext.getCmp('"+this.idPrefix+(idx+1)+"').heredar(true);}" +
    			"    }," +
    			"    change:{" +
    			"        fn:function(){this.heredar();}" +
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

	public String getCdgarant() {
		return cdgarant;
	}

	public void setCdgarant(String cdgarant) {
		this.cdgarant = cdgarant;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getCdrol() {
		return cdrol;
	}

	public void setCdrol(String cdrol) {
		this.cdrol = cdrol;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public Item getColumns() {
		return columns;
	}

	public void setColumns(Item columns) {
		this.columns = columns;
	}
    
}
