<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>Configurador de Pantallas - Vista Previa</title>

<!-- Estilos para extJs -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="/AON-Integrado-local/resources/extjs/local/ext-lang-es.js"></script>

    <script type="text/javascript">
    	Ext.onReady(function(){

	    Ext.QuickTips.init();
	    Ext.QuickTips.enable();
	    
	    // turn on validation errors beside the field globally
	    Ext.form.Field.prototype.msgTarget = 'side';
	    
	    /* override disable para el button*/
		Ext.override(Ext.Button,{disabled: true});
	    //var _ACTION_COMBOS = '<s:url action="obtenerListaComboOttabval" namespace="flujocotizacion" />'; 
	    //alert(_ACTION_COMBOS);
	    
	    
	    <s:if test="%{#session.containsKey('PANTALLA_VISTA_PREVIA')}">
			<s:component template="elementosVistaPreviaPantalla.vm" templateDir="templates" theme="components" >
				<s:param name="PANTALLA_VISTA_PREVIA" value="%{#session['PANTALLA_VISTA_PREVIA']}" />
        	</s:component>
    	</s:if>
	    
	    });
    </script>
</head>
<body>
    <div>
        <table style="width: 98%; border: 0px solid;">
            <tr>
                <td align="left"><div id="items" /></div></td>
            </tr>
           
        </table>
    </div>
   
</body>
</html>