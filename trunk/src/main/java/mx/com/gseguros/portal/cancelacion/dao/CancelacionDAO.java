package mx.com.gseguros.portal.cancelacion.dao;

import java.util.List;
import java.util.Map;

public interface CancelacionDAO
{
    public List<Map<String,String>> buscarPolizas(Map<String,String> params) throws Exception;
}