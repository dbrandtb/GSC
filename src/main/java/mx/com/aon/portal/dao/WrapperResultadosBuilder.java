package mx.com.aon.portal.dao;

import mx.com.aon.portal.util.WrapperResultados;

import java.util.Map;

public interface WrapperResultadosBuilder {
    public WrapperResultados build(Map map) throws Exception;
}
