package mx.com.aon.portal.dao;

import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.core.JdbcTemplate;
import mx.com.aon.portal.util.WrapperResultados;

import javax.sql.DataSource;
import java.util.Map;


public abstract class CustomStoredProcedure extends StoredProcedure {


    protected CustomStoredProcedure() {
    }

    protected CustomStoredProcedure(DataSource dataSource, String string) {
        super(dataSource, string);
    }

    protected CustomStoredProcedure(JdbcTemplate jdbcTemplate, String string) {
        super(jdbcTemplate, string);
    }

    public abstract WrapperResultados mapWrapperResultados(Map map) throws Exception;
}
