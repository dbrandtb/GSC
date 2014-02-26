<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Consulta Clausulas</title>
        
        <script type="text/javascript">
            ////// variables //////
            var _CONTEXT = '${ctx}';
            var _URL_CONSULTA_DATOS_TARIFA_POLIZA = '<s:url namespace="/consultasPoliza" action="consultaDatosTarifaPoliza" />';
            //var _URL_CARGA_CLAVES_CLAU =    '<s:url namespace="/catalogos" action="cargaClausulas" />';
            //var _URL_CONSULTA_CLAUSU =      '<s:url namespace="/catalogos" action="consultaClausulas" />';
            //var _URL_CONSULTA_CLAUSU_DETALLE =      '<s:url namespace="/catalogos" action="consultaClausulaDetalle" />';
            //var _URL_INSERTA_CLAUSU =      '<s:url namespace="/catalogos" action="insertaClausula" />';
            //var _URL_ACTUALIZA_CLAUSU =      '<s:url namespace="/catalogos" action="actualizaClausula" />';
            var _11_params = <s:property value='%{getParams().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;
            
            var _11_itemsForm = [ <s:property value="imap.itemsForm" /> ];
            
            var _11_urlGuardar = '<s:url namespace="/siniestros" action="guardarAfiliadosAfectados" />';
            
            var _11_form;
            
            var recordsStore = [];
            
            <s:set name="contador" value="0" />
            <s:iterator value="slist1">
                recordsStore.push(
                {
                	IdReclamacion    : '<s:property value='%{getSlist1().get(#contador).get("NMSINIES")}' />'
                	,NoAutorizacion  : '<s:property value='%{getSlist1().get(#contador).get("NMAUTSER")}' />'
                	,codAfiliado     : '<s:property value='%{getSlist1().get(#contador).get("CDPERSON")}' />'
                	,nombre          : '<s:property value='%{getSlist1().get(#contador).get("NOMBRE")}'   />'
                	,fechaOcurrencia : '<s:property value='%{getSlist1().get(#contador).get("FEOCURRE")}' />'
                	,noPoliza        : '<s:property value='%{getSlist1().get(#contador).get("NMPOLIZA")}' />'
                	,VoBoAuto        : '<s:property value='%{getSlist1().get(#contador).get("VOBOAUTO")}' />'
                	,icd             : '<s:property value='%{getSlist1().get(#contador).get("CDICD")}'    />'
                	,icdSecundario   : '<s:property value='%{getSlist1().get(#contador).get("ICD2")}'     />'
                	,porcDescuento   : '<s:property value='%{getSlist1().get(#contador).get("DESCPORC")}' />'
                	,impoDescuento   : '<s:property value='%{getSlist1().get(#contador).get("DESCNUME")}' />'
                	,copago          : '<s:property value='%{getSlist1().get(#contador).get("COPAGO")}'   />'
                	,impFacturado    : '<s:property value='%{getSlist1().get(#contador).get("PTIMPORT")}' />'
                	,autoFacturado   : '<s:property value='%{getSlist1().get(#contador).get("AUTRECLA")}' />'
                	,noReclamo       : '<s:property value='%{getSlist1().get(#contador).get("NMRECLAM")}' />'
                	,AUTRECLA        : '<s:property value='%{getSlist1().get(#contador).get("AUTRECLA")}' />'
                	,COMMENAR        : '<s:property value='%{getSlist1().get(#contador).get("COMMENAR")}' />'
                	,AUTMEDIC        : '<s:property value='%{getSlist1().get(#contador).get("AUTMEDIC")}' />'
                    ,COMMENME        : '<s:property value='%{getSlist1().get(#contador).get("COMMENME")}' />'
                });
                <s:set name="contador" value="#contador+1" />
            </s:iterator>
            
            var _11_columnas = [
								{
									xtype         : 'actioncolumn'
									,menuDisabled : true
									,header       : 'Capturar<br/>Detalle'
									,width        : 60
									,align        : 'center'
									,items        :
									[
									    {
									    	icon     : '${ctx}/resources/fam3icons/icons/folder.png'
									    	,tooltip : 'Capturar Detalle'
									    	,handler : revisarDocumento
									    }
									]//,flex:1
								},
                                {   
                                    text            :'Id<br/>Sini.',          width           : 50,
                                    align           :'center',                              dataIndex       :'IdReclamacion'                
                                },
                                {   
                                    text            :'# Auto.',          width           : 50,
                                    align           :'center',                                          dataIndex       :'NoAutorizacion'              
                                }
                                ,
                                {   
                                    text            :'Clave<br/>asegu.',        width           : 70,
                                    align           :'center',                              dataIndex       :'codAfiliado'              
                                },
                                {   
                                    text            :'Nombre<br/>Asegurado',              width           : 110,
                                    align           :'center',                              dataIndex       :'nombre'
                                },
                                {
                                    text            :'Fecha<br/>Ocurrencia',                    width           : 75,
                                    align           :'center',                              dataIndex       :'fechaOcurrencia',
                                    format          :'d/m/y',                               xtype           :'datecolumn'
                                },
                                {
                                    text            :'P&oacute;liza',                       width           : 50,
                                    align           :'center',                              dataIndex       :'noPoliza'
                                },
                                {
                                    text            :'Vo.Bo.<br/>Auto.',      width           : 50,
                                    align           :'center',                              dataIndex       :'VoBoAuto',
                                    renderer        : function(v)
                                    {
                                        var r=v;
                                        if(v=='S'||v=='s')
                                        {
                                            r='SI';
                                        }
                                        else if(v=='N'||v=='n')
                                        {
                                            r='NO';
                                        }
                                        return r;
                                    }
                                },
                                {
                                    text            :'ICD',                                 width           : 50,
                                    align           :'center',                              dataIndex       :'icd'
                                    
                                },
                                {
                                    text            :'ICD2',                     width           : 50, 
                                    align           :'center',                                  dataIndex       :'icdSecundario'
                                },
                                /*{
                                    text            : 'CPT/HCPC',                       width           : 110,
                                    align           :'center',                          dataIndex       :'cpthcpc'
                                },
                                {
                                    text            :'Cantidad',                        width           : 110,  
                                    align           :'center',                          dataIndex       :'cantidad'
                                },
                                {
                                    text            :'Importe <br/>Arancel',            width           : 110,          renderer        :Ext.util.Format.usMoney,
                                    align           :'center',                          dataIndex       :'importeArancel'
                                },
                                {
                                    text            :'Subtotal <br/>Arancel',           width           : 110,          renderer        :Ext.util.Format.usMoney, 
                                    align           :'center',                          dataIndex       :'subtoArancel'
                                },*/
                                {
                                    text            :'%<br/>Desc.',  
                                    dataIndex       :'porcDescuento',
                                    align           :'center',
                                    width           :50
                                },
                                {
                                    text            :'$<br/>Desc.',  
                                    dataIndex       :'impoDescuento',
                                    align           :'center',
                                    width           :80
                                    ,renderer       :Ext.util.Format.usMoney
                                },
                                {
                                    text            :'Copago',                          width           : 50,          
                                    align           :'center',
                                    dataIndex       :'copago'
                                },
                                {
                                    text            :'$<br/>Facturado', 
                                    align           :'center',
                                    dataIndex       :'impFacturado',
                                    	width           :80
                                        ,renderer       :Ext.util.Format.usMoney
                                },
                                {
                                    text            :'#<br/>Reclamo',
                                    align           :'center',
                                    dataIndex       :'noReclamo',
                                    width           :60
                                },
                                <s:property value="imap.columnas" />
                            ];
            
            debug('_11_params:',_11_params);
            ////// variables //////
            
            ////// funciones //////
            function revisarDocumento(grid,rowIndex)
            {
            	var record = grid.getStore().getAt(rowIndex);
            	debug('record.raw:',record.raw);
            	
            	var valido = true;
            	valido = record.get('VoBoAuto')!='n'&&record.get('VoBoAuto')!='N';
            	if(!valido)
            	{
            		mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno autom&aacute;tico');
            	}
            	
            	valido = record.get('AUTRECLA')!='n'&&record.get('AUTRECLA')!='N';
            	if(!valido)
                {
                    mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno del &aacute;rea de reclamaciones');
                }
            	
            	valido = record.get('AUTMEDIC')!='n'&&record.get('AUTMEDIC')!='N';
                if(!valido)
                {
                    mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno del &aacute;rea m&eacute;dica');
                }
            }
            ////// funciones //////
            
        </script>
        <!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>-->
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/afiliadosAfectados.js"></script>
        
<script>
Ext.onReady(function()
{
    _11_form.items.items[3].on('blur',function()
    {
        var comboCoberturas = _11_form.items.items[4];
        comboCoberturas.getStore().load(
        {
            params :
            {
                'params.cdramo'    : _11_form.items.items[2].getValue()
                ,'params.cdtipsit' : _11_form.items.items[3].getValue()
            }
        });
    });
    
    _11_form.items.items[4].on('focus',function()
    {
        var comboCoberturas = _11_form.items.items[4];
        comboCoberturas.getStore().load(
        {
            params :
            {
                'params.cdramo'    : _11_form.items.items[2].getValue()
                ,'params.cdtipsit' : _11_form.items.items[3].getValue()
            }
        });
    });
    
    //recarga al render
    var comboCoberturas = _11_form.items.items[4];
    comboCoberturas.getStore().load(
    {
        params :
        {
            'params.cdramo'    : _11_form.items.items[2].getValue()
            ,'params.cdtipsit' : _11_form.items.items[3].getValue()
        }
    });
    //recarga al render
    
    
});

</script>
        
    </head>
    <body>
    <!-- <div style="height:500px;">
            <div id="div_clau"></div>
   </div>-->
    <div style="height:900px;">
            <div id="div_clau"></div>
            <div id="divResultados" style="margin-top:10px;"></div>
        </div>
    </body>
</html>