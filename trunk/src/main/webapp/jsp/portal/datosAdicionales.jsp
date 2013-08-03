<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
var el_formDatosAdicionales;
function CrearDatosAdicionales () {
        readerDatosAdicionales = new Ext.data.JsonReader( {
            root : 'comboEstadoCivil',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'string'
        }, {
            name : 'texto',
            type : 'string',
            mapping : 'texto'
        }]);
		var dsDatosAdicionales = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_ESTADO_CIVIL
            }),
            reader: readerDatosAdicionales
        });		
        var el_formDatosAdicionales = new Ext.FormPanel ({
			renderTo: 'formDatosAdicionales',
            url : _ACTION_OBTENER_DATOS_ADICIONALES,
            reader: readerDatosAdicionales,
            frame : true,
            width : 600,
            height: 350,
            waitMsgTarget : true,
            successProperty: 'success',
			items: [{
            			layout: 'column',
            			items: [
            			{
            				columnWidth: .5,
            				layout: 'form',
            				items: 
            					<s:component templateDir="template" template="testfield.vm" theme="components">
            						<s:param name="modelControl" value="#session.modelControl"/>
            					</s:component>
            					<%=session.getAttribute("modelControl")%>
            			}
            			]
            		}],
            		buttonAlign: 'center',
            		buttons: [
            					{text: 'Guardar', handler: function () {Ext.Msg.alert('Error', 'No implementado...');}},
            					{text: 'Guardar y Agregar', handler: function () {Ext.Msg.alert('Error', 'No implementado...');}},
            					{text: 'Regresar', handler: function() {window.location.href = _ACTION_REGRESAR;}}
            				]

            //se definen los campos del formulario
	});
	el_formDatosAdicionales.load({
			params: {
					codigoPersona: CODIGO_PERSONA,
					codigoTipoPersona: TIPO_PERSONA,
					codigoAtributo: ''
			}
	});
	return el_formDatosAdicionales;
}
</script>