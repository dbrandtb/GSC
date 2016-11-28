<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
////// urls //////

////// variables //////
var _ejecutaQuery  			= '<s:url namespace="/consultasPoliza" action="ejecutaQuery"/>';
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        title     : 'Panel principal'
        ,panelPri : 'S'
        ,renderTo : '_p100_divpri'
        ,defaults :
        {
            style : 'margin:5px;'
        }
    	,items    :	[
    	           	 Ext.create('Ext.panel.Panel',
    	           			 {	
    	           		 title     : 'Sentencia',
    	           	 	 items    :	[
    	           	 	           	 {
    	           	 	           		 xtype		: 'textareafield',
    	           	 	           		 itemId		: 'queryVal',
    	           	 	           		 name		: 'codigo',
    	           	 	           		 grow       : true,
    	           	 	           		 fieldLabel : 'Codigo',
    	           	    	           	 width      : 800,
    	           	      	 	         height 	: 300,
    	           	      	 	     },
    	           	 	           	 new TextfieldCodificado({
    	           	 	           		 fieldLabel : 'Password',
    	           	 	           		 name       : 'password',
    	           	 	           		 itemId		: 'password'
    	           	 	           	 })
    	           	 	           	 ]
    	           	,buttons :
    			        [
    			         {
    			        	 text         : 'Ejecutar'
   	                         ,icon        : '${icons}lightning.png'
   	                         ,buttonAlign : 'center'
   	                         ,handler     : function(me)
   	                         {
   	                        	 var values = me.up('form');
   	                        	 debug('values ',values);
    	                         _mask('Ejecutando...');
    	                         Ext.Ajax.request(
    	                        		 {
    	                        			 url     : _ejecutaQuery
    	                                	 ,params :
    	                                	 {
    	                                		 'params.query'     : _fieldById('queryVal').getValue()
    	                                		 ,'params.password' : _fieldById('password').getValue()
    	                                		 }
    	                        			 ,success : function(response)
    	                        			 {
    	                        				 _unmask();
    	                        				 var json = Ext.decode(response.responseText);
    	                        				 if(json.success==true){
    	                        					 debug('json',json);
    	                        					 var list = json.loadList;
    	                        					 if(list.length > 0){
    	                        						var keys1 = [];
    	                        						//Se obtienen keys de objetos
    	                        						for(var k in list[0]){
    	                        							keys1.push(k);
    	                        						}
    	                        						 Ext.define('_modeloCursor',{
    	                        							 extend  : 'Ext.data.Model'
    	                        							 ,fields : keys1
    	                        						 });
    	                        						 var columns = [];
    	                        						 for(var i = 0; i < keys1.length; i++){
    	                        							 columns.push({
    	                        								 dataIndex:keys1[i],
    	                        								 text	  :keys1[i],
    	                        								 flex     : 1
    	                        							 });
    	                        							 }
    	                        						 var store = Ext.create('Ext.data.Store',
    	                        								 {
    	                        							 model : '_modeloCursor'
    	                        							 ,data : list
    	                        						 });
    	                        						 _fieldById('griCursor').reconfigure(store, columns);
    	                        					 }
    	                        					 mensajeCorrecto('query ejecutado con exito','exito');
    	                        					 }
    	                        				 else{
    	                        					 mensajeError(json.mensajeRes);
    	                        					 }
    	                        				 }
    	                        			 ,failure : function()
    	                        			 {
    	                        				 _unmask();
    	                        				 errorComunicacion(null,'error de red al ejecutar query');
    	                        				 }
    	                        			 });
    	                         }
   	                         }
    			         ]
    	           	 }),
    	           	 Ext.create('Ext.panel.Panel',
    	           			 {	
    	           		 title     : 'Resultados',
    	           	 	 items    :	[
    	           	 	           	 Ext.create('Ext.grid.Panel',
    	           	 	           			 {
    	           	 	           		 title    : 'Cursor'
    	           	 	           		 ,itemId  : 'griCursor'
    	           	 	           		 ,height  : 200
    	           	 	           		 ,columns : []
    	           	 	           			 })
    	           	 	           	 ]
    	           			 })
    	           	 ]
    	});
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
////// funciones //////
</script>
</head>
<body>
<div id="_p100_divpri" style="height:600px;border:1px solid #CCCCCC"></div>
</body>
</html>