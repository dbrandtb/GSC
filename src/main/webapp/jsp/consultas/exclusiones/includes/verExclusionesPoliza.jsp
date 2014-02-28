<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Exclusiones de p&oacute;liza</title>

<script type="text/javascript">
            var venExcluContexto = '${ctx}';
            
            var _cdUnieco =  '<s:property value="params.cdunieco" />';
            var _cdRamo =    '<s:property value="params.cdramo" />';
            var _estado =    '<s:property value="params.estado" />';
            var _nmpoliza =  '<s:property value="params.nmpoliza" />';
            var _nmsituac =  '<s:property value="params.nmsituac" />';
            
            var venExcluUrlCargar = '<s:url namespace="/" action="cargarPantallaExclusion" />';
            
	Ext.onReady(function() {

		 Ext.define('ModeloExclusion',{
		        extend:'Ext.data.Model',
		        fields:['cdclausu','dsclausu','linea_usuario','cdtipcla','linea_general','merged']
		    });
		    
		
		var venExcluStoreUsa = new Ext.data.Store(
			    {
			        model      : 'ModeloExclusion'
			        //,autoLoad  : true
			        ,proxy     :
			        {
			            url     : venExcluUrlCargar
			            ,extraParams :
			            {
			                'smap1.pv_cdunieco'  : _cdUnieco
			                ,'smap1.pv_cdramo'   : _cdRamo
			                ,'smap1.pv_estado'   : _estado
			                ,'smap1.pv_nmpoliza' : _nmpoliza
			                ,'smap1.pv_nmsituac' : _nmsituac
			            }
			            ,type   : 'ajax'
			            ,reader :
			            {
			                type  : 'json'
			                ,root : 'slist1'
			            }
			        }
			    });
		venExcluStoreUsa.load();
		
		Ext.create('Ext.grid.Panel',
	            {
	                title          : 'Exclusiones de P&oacute;liza'
	                ,store         : venExcluStoreUsa
	                ,collapsible   : true
	                ,titleCollapse : true
	                ,style         : 'margin:5px'
	                ,height        : 200
	                ,renderTo     : 'maindivExcs'
	                ,buttonAlign   : 'center'
	                ,columns       :
	                [
	                    {
	                        header     : 'Nombre'
	                        ,dataIndex : 'dsclausu'
	                        ,flex      : 1
	                    }
	                    
	                ]
	                ,buttons:
	                [{
	                        text     : 'Aceptar'
	                        ,icon    : venExcluContexto+'/resources/fam3icons/icons/accept.png'
	                        ,handler : function()
	                        {
	                            
	                        }
	                 }]
	            });
    
		
            	
    });            
            
            

</script>

</head>
<body>
<div id="maindivExcs" style="height:400px;"></div>
</body>
</html>