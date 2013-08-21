<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
            var _URL_ASEGURAR='<s:url action="saludbasica" namespace="/" />';
            var _URL_JSON_OBTEN_CIUDADES='<s:url action="jsonObtenCiudades" namespace="/" />';
            var _URL_JSON_OBTEN_COPAGOS='<s:url action="jsonObtenCopagos" namespace="/" />';
            var _URL_JSON_OBTEN_SUMAS_ASEGURADAS='<s:url action="jsonObtenSumasAseguradas" namespace="/" />';
            var _URL_JSON_OBTEN_CIRCULOS_HOSPITALARIOS='<s:url action="jsonObtenCirculosHospitalarios" namespace="/" />';
            var _URL_JSON_OBTEN_COBERTURA_VACUNAS='<s:url action="jsonObtenCoberturaVacunas" namespace="/" />';
            var _URL_JSON_OBTEN_COBERTURA_PREVENCION_ENFERMEDADES_ADULTOS='<s:url action="jsonObtenCoberturaPrevencionEnfermedadesAdultos" namespace="/" />';
            var _URL_JSON_OBTEN_MATERNIDAD='<s:url action="jsonObtenMaternidad" namespace="/" />';
            var _URL_JSON_OBTEN_SUMA_ASEGURADA_MATERNIDAD='<s:url action="jsonObtenSumaAseguradaMaternidad" namespace="/" />';
            var _URL_JSON_OBTEN_BASE_TABULADOR_REEMBOLSO='<s:url action="jsonObtenBaseTabuladorReembolso" namespace="/" />';
            var _URL_JSON_OBTEN_COSTO_EMERGENCIA_EXTRANJERO='<s:url action="jsonObtenCostoEmergenciaExtranjero" namespace="/" />';
            var _URL_JSON_OBTEN_GENEROS='<s:url action="jsonObtenGeneros" namespace="/" />';
            var _URL_JSON_OBTEN_ROLES='<s:url action="jsonObtenRoles" namespace="/" />';
        </script>
        <script src="${ctx}/resources/jsp-script/extjs4/asegurados.js"></script>
    </head>
    <body>
        <div id="maindiv"></div>
    </body>
</html>
