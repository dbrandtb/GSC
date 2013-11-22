<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cancelar</title>
<script>
    ///////////////////////
    ////// variables //////
    /*///////////////////*/
    var panCanUrlCat     = '<s:url namespace="/flujocotizacion"  action="cargarCatalogos" />';
    var panCanUrlAgentes = '<s:url namespace="/mesacontrol"      action="obtieneAgentes" />';
    /*///////////////////*/
    ////// variables //////
    ///////////////////////
    
    ///////////////////////
    ////// funciones //////
    /*///////////////////*/
    
    /*///////////////////*/
    ////// funciones //////
    ///////////////////////
    
Ext.onReady(function()
{
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*
    Ext.define('Modelo1',
    {
        extend:'Ext.data.Model'
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*
    Ext.define('Panel1',{
        extend:'Ext.panel.Panel',
        title:'Objeto asegurado',
        layout:{
            type:'column',
            columns:2
        },
        frame:false,
        style:'margin:5px;',
        collapsible:true,
        titleCollapse:true,
        <s:property value="item2" />
    });
    Ext.define('FormPanel',{
        extend:'Ext.form.Panel',
        renderTo:'maindiv',
        frame:false,
        buttonAlign:'center',
        items:[
            new Panel1()
        ]
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    Ext.create('Ext.form.Panel',
    {
    	renderTo     : 'panCanDivPri'
    	,title       : 'Cancelaci&oacute;n'
    	,bodyPadding : 5
    	,items       :
    	[
    	    Ext.create('Ext.form.field.ComboBox',
	        {
	            fieldLabel      : 'Sucursal'
	            ,name           : 'smap1.pv_cdsucdoc_i'
	            ,allowBlank     : false
	            ,editable       : false
	            ,displayField   : 'value'
	            ,valueField     : 'key'
	            ,forceSelection : true
	            ,queryMode      :'local'
	            ,store          : Ext.create('Ext.data.Store',
	            {
	                model     : 'Generic'
	                ,autoLoad : true
	                ,proxy    :
	                {
	                    type         : 'ajax'
	                    ,url         : panCanUrlCat
	                    ,extraParams : {catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_DOCU" />'}
	                    ,reader      :
	                    {
	                        type  : 'json'
	                        ,root : 'lista'
	                    }
	                }
	            })
	        })
	        ,Ext.create('Ext.form.field.ComboBox',
            {
                fieldLabel       : 'Agente'
                ,name            : 'smap1.pv_cdagente_i'
                ,allowBlank      : false
                ,displayField    : 'value'
                ,valueField      : 'key'
                ,forceSelection  : true
                ,matchFieldWidth : false
                ,hideTrigger     : true
                ,minChars        : 3
                ,queryMode       : 'remote'
                ,queryParam      : 'smap1.pv_cdagente_i'
                ,store           : Ext.create('Ext.data.Store',
                {
                    model     : 'Generic'
                    ,autoLoad : false
                    ,proxy    :
                    {
                        type    : 'ajax'
                        ,url    : panCanUrlAgentes
                        ,reader :
                        {
                            type  : 'json'
                            ,root : 'lista'
                        }
                    }
                })
            })
    	]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*
    Ext.define('LoaderForm',
    {
        extend:'Modelo1',
        proxy:
        {
            extraParams:{
            },
            type:'ajax',
            url : urlCargar,
            reader:{
                type:'json'
            }
        }
    });

    var loaderForm=Ext.ModelManager.getModel('LoaderForm');
    loaderForm.load(123, {
        success: function(resp) {
            //console.log(resp);
            formPanel.loadRecord(resp);
        },
        failure:function()
        {
            Ext.Msg.show({
                title:'Error',
                icon: Ext.Msg.ERROR,
                msg: 'Error al cargar',
                buttons: Ext.Msg.OK
            });
        }
    });
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
});
</script>
</head>
<body>
<div id="panCanDivPri"></div>
</body>
</html>