/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.util.List;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Jair
 */
public class GeneradorCampos
{
	
	private static       Logger log                   = Logger.getLogger(GeneradorCampos.class);
    public static final  String namePrefix            = "parametros.pv_otvalor";
    private static final String formatoFecha          = "d/m/Y";
    private static final String xtypeDatecolumn       = "datecolumn";
    private static final int    staticFlex            = 1;
    private static final String descExcTipoCampoVacio = "El campo no tiene tipo campo (A,N,T,F,P)";
	private static final String descExcTipoCampoOtro  = "El campo tiene tipo campo incorrecto (A,N,T,F,P)";
    
    public  String idPrefix;
    private Item   items;
    private Item   fields;
    private Item   columns;
    private String context;
    private String cdgarant;
    private String cdramo;
    private String cdrol;
    private String cdtipsit;
    
    private boolean parcial   = false;
    private boolean conEditor = false;
    
    public GeneradorCampos(String context)
    {
    	this.context="/"+context;
    	log.debug("contexto para el generador de campos: "+this.context);
    }
    
    /**
     * Genera componentes con la cadena (fields, columns o items) sin nombre.
     * Los objetos JSON generados van en llaves sin pertenecer a un padre ni corchetes.
     * Completo :
     * "fields : [ {...}, ... {...} ]"
     * Parcial  :
     * "{...}, ... {...}"
     */
    public void generaParcial(List<ComponenteVO> listcomp) throws Exception
    {
        this.parcial=true;
        this.genera(listcomp);
        this.parcial=false;
    }
    
    /**
     * Genera componentes cuyas columnas tienes editor.
     * Sirve para grids editables.
     * Genera el padre (columns, fields o items) y los corchetes que encierran los hijos.
     * "columns :
     * [
     *    {
     *        header     : 'columna'
     *        ,dataIndex : 'index'
     *        ,editor    :
     *        {
     *            xtype       : 'textfield'
     *            ,allowBlank : false
     *        }
     *    },
     *    ...
     *    {...}
     * ]"
     */
    public void generaConEditor(List<ComponenteVO> listcomp) throws Exception
    {
    	this.conEditor=true;
    	this.genera(listcomp);
    	this.conEditor=false;
    }
    
    /**
     * Genera componentes parciales cuyas columnas tienen editor.
     * Sirve para grids editables.
     * Los objetos JSON generados no tienen padre (columns, items o fields) ni estan entre corchetes.
     * "{
     *     header     : 'columna'
     *     ,dataIndex : 'index'
     *     ,editor    :
     *     {
     *         xtype       : 'textfield'
     *         ,allowBlank : false
     *     }
     * },
     * ...
     * { ... }"
     */
    public void generaParcialConEditor(List<ComponenteVO> listcomp) throws Exception
    {
    	this.parcial=true;
    	this.conEditor=true;
    	this.genera(listcomp);
    	this.parcial=false;
    	this.conEditor=false;
    }
    
    public void genera(List<ComponenteVO> listcomp) throws Exception
    {
    	String itemsKey   = null;
    	String fieldsKey  = null;
    	String columnskey = null;
    	
    	if(!this.parcial)
    	{
    		itemsKey   = "items";
    		fieldsKey  = "fields";
    		columnskey = "columns";
    	}
    	
    	idPrefix="idAutoGenerado_"+System.currentTimeMillis()+"_"+((long)Math.ceil((Math.random()*10000d)))+"_";
    	
        items  = new Item(itemsKey   , null , Item.ARR);
        fields = new Item(fieldsKey  , null , Item.ARR);
        columns= new Item(columnskey , null , Item.ARR);
        
        if(listcomp!=null&&!listcomp.isEmpty())
        {
            for(int i=0;i<listcomp.size();i++)
            {
                this.generaCampoYFieldYColumn(listcomp, listcomp.get(i), i);
            }
        }
        
        log.debug(fields.toString());
        log.debug(items.toString());
        log.debug(columns.toString());
    }
    
    /**
     * Genera un item para formulario o un editor para grid segun parametro de entrada esEditor
     */
    private Item generaItem(List<ComponenteVO> listcomp, ComponenteVO comp, Integer idx, boolean esEditor) throws Exception
    {
    	boolean esCombo   = StringUtils.isNotBlank(comp.getCatalogo());
    	String  tipoCampo = comp.getTipoCampo();
    	
        Item item=new Item();
        item.setType(Item.OBJ);
        
        ////// Ext.create('Ext...',{}) //////
        if(esCombo)
        {
        	item.setComposedName("Ext.create('Ext.form.ComboBox',{");
        }
        else if(tipoCampo.equals(ComponenteVO.TIPOCAMPO_ALFANUMERICO))
        {
        	item.setComposedName("Ext.create('Ext.form.TextField',{");
        }
        else if(tipoCampo.equals(ComponenteVO.TIPOCAMPO_TEXTAREA))
        {
        	item.setComposedName("Ext.create('Ext.form.TextArea',{");
        }
        else if(tipoCampo.equals(ComponenteVO.TIPOCAMPO_NUMERICO)||tipoCampo.equals(ComponenteVO.TIPOCAMPO_PORCENTAJE))
        {
        	item.setComposedName("Ext.create('Ext.form.NumberField',{");
        }
        else if(tipoCampo.equals(ComponenteVO.TIPOCAMPO_FECHA))
        {
        	item.setComposedName("Ext.create('Ext.form.DateField',{");
        }
        item.setComposedNameClose("})");
        ////// Ext.create('Ext...',{}) //////
        
        ////// id, cdatribu, fieldLabel, allowBlank, name, readOnly, value, hidden, style //////
        String auxIdEditor = "";
        if(esEditor)
        {
        	auxIdEditor = "editor_";
        }
        String compId = this.idPrefix+auxIdEditor+idx; 
        
        String cdatribu = comp.getNameCdatribu();
        if(cdatribu==null)
        {
        	cdatribu = "";
        }
        
        String fieldLabel = comp.getLabel();
        if(fieldLabel==null||esEditor)
        {
        	fieldLabel = "";
        }
        
        String name = comp.getNameCdatribu();
        if(name==null)
        {
        	name = "";
        }
        if(comp.isFlagEsAtribu())
        {
        	if(name.length()<2)
        	{
        		name = "0" + name;
        	}
        	name = GeneradorCampos.namePrefix + name;
        }
        
        String value=comp.getValue();
        
        item.add("id"         , compId);
        item.add("cdatribu"   , cdatribu);
        item.add("fieldLabel" , fieldLabel);
        item.add("allowBlank" , !comp.isObligatorio());
        item.add("name"       , name);
        item.add("readOnly"   , comp.isSoloLectura());
        if(StringUtils.isNotBlank(value))
        {
        	item.add(Item.crear("value" , value).setQuotes(""));
        }
        item.add("hidden"     , comp.isOculto());
        item.add("style"      , "margin:5px");
        ////// id, cdatribu, fieldLabel, allowBlank, name, readOnly, value, hidden, style //////
        
        ////// format //////
        if(tipoCampo.equals(ComponenteVO.TIPOCAMPO_FECHA))
        {
        	item.add("format",GeneradorCampos.formatoFecha);
        }
        ////// format //////
        
        ////// minLength, maxLength //////
        if((tipoCampo.equals(ComponenteVO.TIPOCAMPO_ALFANUMERICO)
        		||tipoCampo.equals(ComponenteVO.TIPOCAMPO_TEXTAREA)
        		||tipoCampo.equals(ComponenteVO.TIPOCAMPO_NUMERICO)
        		||tipoCampo.equals(ComponenteVO.TIPOCAMPO_PORCENTAJE)
        		)&&!esCombo
        		)
        {
	        if(comp.isFlagMinLength())
	        {
	        	item.add("minLength" , comp.getMinLength());
	        }
	        if(comp.isFlagMaxLength())
	        {
	        	item.add("maxLength" , comp.getMaxLength());
	        }
        }
        ////// minLength, maxLength //////
        
        ////// es padre //////
        boolean esPadre = idx<listcomp.size()-1&&listcomp.get(idx+1).isDependiente();
        if(esPadre&&!esCombo)//si es combo no se pone porque se sobreescribe
        {
        	this.agregarHerenciaPadre(listcomp,item,idx,esEditor);
        }
        ////// es padre //////
        
        ////// combo //////
        if(esCombo)
        {
        	boolean esHijo          = comp.isDependiente();
        	boolean esAutocompleter = StringUtils.isNotBlank(comp.getQueryParam());
        	
        	////// typeAhead, displayField, valueField //////
        	item.add("typeAhead"    , true);
        	item.add("displayField" , "value");
            item.add("valueField"   , "key");
            ////// typeAhead, displayField, valueField //////
        	
        	////// forceSelection, editable //////
            boolean editable = false;
            if(esAutocompleter)
            {
            	editable = true;
            }
            item.add("forceSelection" , editable);
            item.add("editable"       , editable);
        	////// forceSelection, editable //////
        	
        	////// queryMode //////
            String queryMode = "local";
            if(esAutocompleter)
            {
            	queryMode = "remote";
            }
        	item.add("queryMode", queryMode);
        	////// queryMode //////
        	
        	////// herencia //////
        	boolean listeners = false;
        	if(esPadre)
            {
            	if(esHijo)
            	{
            		this.agregarHerenciaPadreHijo(listcomp, item, idx, esEditor);
            	}
            	else
            	{
            		this.agregarHerenciaPadre(listcomp, item, idx, esEditor);
            	}
            	listeners=true;
            }	
            if(esHijo)
            {
            	String auxCompAnteIdEditor = "";
                if(esEditor)
                {
                	auxCompAnteIdEditor = "editor_";
                }
                String compAnteriorId = this.idPrefix+auxCompAnteIdEditor+(idx-1);
                
                //it.add(Item.crear("forceSelection",false));??
                item.add(Item.crear(""
                		+ "heredar",
                		  "function(remoto)"
                		+ "{"
                		+ "    debug('Heredar "+name+"');"
                		+ "    if(!this.noEsPrimera||remoto==true)"
                		+ "    {"
                		+ "        debug('Hereda por primera vez o porque la invoca el padre');"
                		+ "        this.noEsPrimera=true;"
                		+ "        this.getStore().load("
                		+ "        {"
                		+ "            params    :"
                		+ "            {"
                		+ "                'params.idPadre':Ext.getCmp('"+compAnteriorId+"').getValue()"
                		+ "            }"
                		+ "            ,callback : function()"
                		+ "            {"
                		+ "                var thisCmp=Ext.getCmp('"+compId+"');"
                		+ "                var valorActual=thisCmp.getValue();"
                		+ "                var dentro=false;"
                		+ "                thisCmp.getStore().each(function(record)"
                		+ "                {"
                		+ "                    if(valorActual==record.get('key'))"
                		+ "                    {"
                		+ "                        dentro=true;"
                		+ "                    }"
                		+ "                });"
                		+ "                if(!dentro)"
                		+ "                {"
                		+ "                    thisCmp.clearValue();"
                		+ "                }"
                		+ "            }"
                		+ "        });"
                		+ "    }"
                		+ "    else"
                		+ "    {"
                		+ "        debug('No hereda porque es un change repetitivo');"
                		+ "    }"
                		+ "}"
                		+ (listeners?
                		  "":
                		  ",listeners:"
                		+ "{"
                		+ "    change       :"
                		+ "    {"
                		+ "        fn:function()"
                		+ "        {"
                		+ "            this.heredar();"
                		+ "        }"
                		+ "    }"
                		+ "}")
                		).setQuotes(""));
            }
            ////// herencia //////
            
            ///////////////////
            ////// store //////
            //////       //////
            Item store=new Item(null,null,Item.OBJ,"store:Ext.create('Ext.data.Store',{","})");
            item.add(store);
            store.add("model","Generic");
            
            ////// autoLoad //////
            boolean autoLoad = true;
            if(esHijo||esAutocompleter)
            {
            	autoLoad=false;
            }
            store.add("autoLoad",autoLoad);
            ////// autoLoad //////
            
            ///////////////////
            ////// proxy //////
            Item proxy=new Item("proxy",null,Item.OBJ);
            store.add(proxy);
            proxy.add("type","ajax");
            proxy.add("url",this.context+"/catalogos/obtieneCatalogo.action");
            proxy.add(
            		Item.crear("reader", null, Item.OBJ)
            		.add("type","json")
            		.add("root","lista")
            		);
            
            ////// extraParams //////
            if(comp.getType()==ComponenteVO.TIPO_TATRISIT)
            {
            	proxy.add(
                        Item.crear("extraParams" , null, Item.OBJ)
                        .add("'params.cdatribu'" , cdatribu)
                        .add("'params.cdtipsit'" , cdtipsit)
                        .add("catalogo"          , Catalogos.TATRISIT.getCdTabla())
                        );
            }
            else if(comp.getType()==ComponenteVO.TIPO_TATRIPOL)
            {
            	proxy.add(
                        Item.crear("extraParams" , null, Item.OBJ)
                        .add("'params.cdatribu'" , cdatribu)
                        .add("catalogo"          , Catalogos.TATRIPOL.getCdTabla())
                        );
            }
            else if(comp.getType()==ComponenteVO.TIPO_TATRIGAR)
            {
            	proxy.add(
                        Item.crear("extraParams" , null, Item.OBJ)
                        .add("'params.cdatribu'" , cdatribu)
                        .add("'params.cdgarant'" , cdgarant)
                        .add("catalogo"          , Catalogos.TATRIGAR.getCdTabla())
                        );
            }
            else if(comp.getType()==ComponenteVO.TIPO_TATRIPER)
            {
            	proxy.add(
                        Item.crear("extraParams", null, Item.OBJ)
                        .add("'params.cdramo'"  , cdramo)
                        .add("'params.cdrol'"   , cdrol)
                        .add("'params.cdatribu'", cdatribu)
                        .add("'params.cdtipsit'", cdtipsit)
                        .add("catalogo"         , Catalogos.TATRIPER.getCdTabla())
                        );
            }
            else if(comp.getType()==ComponenteVO.TIPO_GENERICO)
            {
            	Item extraParams=Item.crear("extraParams", null, Item.OBJ)
            			.add("catalogo",comp.getCatalogo());
            	if(StringUtils.isNotBlank(comp.getParamName1()))
            	{
            		extraParams.add(Item.crear(comp.getParamName1(),comp.getParamValue1()).setQuotes(""));
            	}
            	if(StringUtils.isNotBlank(comp.getParamName2()))
            	{
            		extraParams.add(Item.crear(comp.getParamName2(),comp.getParamValue2()).setQuotes(""));
            	}
            	if(StringUtils.isNotBlank(comp.getParamName3()))
            	{
            		extraParams.add(Item.crear(comp.getParamName3(),comp.getParamValue3()).setQuotes(""));
            	}
            	if(StringUtils.isNotBlank(comp.getParamName4()))
            	{
            		extraParams.add(Item.crear(comp.getParamName4(),comp.getParamValue4()).setQuotes(""));
            	}
            	if(StringUtils.isNotBlank(comp.getParamName5()))
            	{
            		extraParams.add(Item.crear(comp.getParamName5(),comp.getParamValue5()).setQuotes(""));
            	}
            	proxy.add(extraParams);
            }
            ////// extraParams //////
            
            ////// proxy //////
            ///////////////////
            
            //////       //////
            ////// store //////
            ///////////////////
            
            ////// autocompleter //////
            if(esAutocompleter)
            {
            	item.add("hideTrigger" , true);
            	item.add("minChars"    , 3);
            	item.add("queryParam"  , comp.getQueryParam());
            }
            ////// autocompleter //////
        }
        ////// combo //////
        
        return item;
    }
    
    /**
     * Genera un field para Ext.data.Model
     * Ext.define('MiModelo',
     * {
     *     extend  : 'Ext.data.Model'
     *     ,fields :
     *     [
     *         <s:property value="itemFields" />
     *     ]
     * });
     * La cadena imprime:
     * {
     *     name        : 'atributo'
     *     ,type       : 'date'
     *     ,dateFormat : 'd/m/Y'
     * }
     */
    private Item generaField(List<ComponenteVO> listcomp, ComponenteVO comp, Integer idx) throws Exception
    {
    	String tipoAlfanum  = "string";
    	String tipoFecha    = "date";
    	String tipoEntero   = "int";
    	String tipoFlotante = "float";
    	
    	String name = comp.getNameCdatribu();
    	if(comp.isFlagEsAtribu())
    	{
    		String cdatribu = comp.getNameCdatribu();
    		if(cdatribu.length()<2)
    		{
    			cdatribu = "0" + cdatribu;
    		}
    		name    = GeneradorCampos.namePrefix + cdatribu;
    	}
    	
        String type     = tipoAlfanum;
        boolean esCombo = StringUtils.isNotBlank(comp.getCatalogo()); 
        if(!esCombo)
        {
        	String tipo = comp.getTipoCampo();
        	boolean tieneTipo = StringUtils.isNotBlank(tipo);  
        	
        	if(tieneTipo)
        	{
        		if(tipo.equalsIgnoreCase(ComponenteVO.TIPOCAMPO_ALFANUMERICO))
        		{}
        		else if(tipo.equalsIgnoreCase(ComponenteVO.TIPOCAMPO_FECHA))
        		{
        			type = tipoFecha;
        		}
        		else if(tipo.equalsIgnoreCase(ComponenteVO.TIPOCAMPO_NUMERICO))
        		{
        			type = tipoEntero;
        		}
        		else if(tipo.equalsIgnoreCase(ComponenteVO.TIPOCAMPO_PORCENTAJE))
        		{
        			type = tipoFlotante;
        		}
        		else if(tipo.equalsIgnoreCase(ComponenteVO.TIPOCAMPO_TEXTAREA))
        		{}
        		else
        		{
        			throw new Exception(descExcTipoCampoOtro);
        		}
        	}
        	else
        	{
        		throw new Exception(descExcTipoCampoVacio);
        	}
        }

        Item field=new Item();
        field.setType(Item.OBJ);
        field.add("name", name);
        field.add("type", type);
        
        if(type.equals(tipoFecha))
        {
        	field.add(Item.crear("dateFormat", GeneradorCampos.formatoFecha));
        }
        
        return field;
    }
    
    /**
     * Genera una columna para usar en grid.
     * Ext.define('MiGrid',
     * {
     *     extend   : 'Ext.grid.Panel'
     *     ,columns :
     *     [
     *         <s:property value="itemColumn" />
     *     ]
     * });
     */
    private Item generaColumn(List<ComponenteVO> listcomp, ComponenteVO comp, Integer idx) throws Exception
    {
    	Item col = null;
    	
    	String columna = comp.getColumna();
    	boolean hayColumna = StringUtils.isNotBlank(columna)
    			&&(
    				columna.equalsIgnoreCase(Constantes.SI)
    				||columna.equalsIgnoreCase(ComponenteVO.COLUMNA_OCULTA)
    			);
    	
    	if(hayColumna)
    	{
	    	String dataIndex = comp.getNameCdatribu();
	    	if(comp.isFlagEsAtribu())
	    	{
	    		String cdatribu = comp.getNameCdatribu();
	    		if(cdatribu.length()<2)
	    		{
	    			cdatribu = "0" + cdatribu;
	    		}
	    		dataIndex = GeneradorCampos.namePrefix + cdatribu;
	    	}
	    	
	    	String header = comp.getLabel();
	        
	        boolean hidden  = columna.equalsIgnoreCase(ComponenteVO.COLUMNA_OCULTA);
	        String renderer = comp.getRenderer();
	        if(StringUtils.isBlank(renderer))
	        {
	        	renderer = "";
	        }
	        
	        String tipoCampo = comp.getTipoCampo();
	        boolean esFecha = StringUtils.isNotBlank(tipoCampo)&&tipoCampo.equalsIgnoreCase(ComponenteVO.TIPOCAMPO_FECHA);
	    
		    col=new Item();
		    col.setType(Item.OBJ);
		    col.add("header"    , header);
		    col.add("dataIndex" , dataIndex);
		    col.add("flex"      , GeneradorCampos.staticFlex);
		    col.add("hidden"    , hidden);
		    
		    if(StringUtils.isNotBlank(renderer))
		    {
		    	col.add(Item.crear("renderer",renderer).setQuotes(""));
		    }
		    
		    if(esFecha)
		    {
		    	col.add("xtype"  , GeneradorCampos.xtypeDatecolumn);
		    	col.add("format" , GeneradorCampos.formatoFecha);
		    }
    	}
    	
    	return col;
    }
    
    /**
     * Genera el field para el modelo, el item para el formulario, la columna en caso de tener S o H, y su editor
     * en caso de haber sido llamado desde generaConEditor() o desde generaParcialConEditor()
     */
    public void generaCampoYFieldYColumn(List<ComponenteVO> listcomp, ComponenteVO comp, Integer idx) throws Exception
    {
    	Item field  = this.generaField(listcomp, comp, idx);
        Item column = this.generaColumn(listcomp, comp, idx);
        Item item   = this.generaItem(listcomp, comp, idx, false);
        
        Item editor=null;
        if(conEditor)
        {
        	editor=this.generaItem(listcomp, comp, idx, true);
        }
        
        items.add(item);
        fields.add(field);
        if(column!=null)
        {
        	if(conEditor)
        	{
        		column.add("editor",editor);
        	}
        	columns.add(column);
        }
    }

    /**
     * Agrega escuchadores.
     * blur : invoca herencia de su hijo.
     * @param lt
     * @param it
     * @param idx
     * @param editor
     * @throws Exception
     */
    private void agregarHerenciaPadre(List<ComponenteVO> lt, Item it, Integer idx, boolean editor) throws Exception
    {
    	it.add(Item.crear(""
    			+ "listeners",
    			  "{"
    			+ "    blur:"
    			+ "    {"
    			+ "        fn:function()"
    			+ "        {"
    			+ "            debug('blur');"
    			+ "            Ext.getCmp('"+this.idPrefix+(editor?"editor_":"")+(idx+1)+"').heredar(true);"
    			+ "        }"
    			+ "    }"
    			+ "}")
    			.setQuotes(""));
	}
    
    /**
     * Agrega escuchadores.
     * blur        : invoca herencia de su hijo.
     * change      : invoca su propia herencia.
     * afterrender : invoca su propia herencia.
     * @param lt
     * @param it
     * @param idx
     * @param editor
     * @throws Exception
     */
    private void agregarHerenciaPadreHijo(List<ComponenteVO> lt, Item it, Integer idx, boolean editor) throws Exception
    {
    	it.add(Item.crear(""
    			+ "listeners",
    			  "{"
    			+ "    blur         :"
    			+ "    {"
    			+ "        fn:function()"
    			+ "        {"
    			+ "            debug('blur');"
    			+ "            Ext.getCmp('"+this.idPrefix+(editor?"editor_":"")+(idx+1)+"').heredar(true);"
    			+ "        }"
    			+ "    }"
    			+ "    ,change      :"
    			+ "    {"
    			+ "        fn : function()"
    			+ "        {"
    			+ "            this.heredar();"
    			+ "        }"
    			+ "    }"
    			+ "}")
    			.setQuotes(""));
	}

    /////////////////////////////////
    ////// getters and setters //////
    /*/////////////////////////////*/
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