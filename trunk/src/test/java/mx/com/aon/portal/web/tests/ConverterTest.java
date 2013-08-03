package mx.com.aon.portal.web.tests;

import junit.framework.TestCase;

import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.model.UserVO;

import java.sql.Timestamp;


public class ConverterTest extends TestCase {

    public void testDateConverter() {
        try {

//            ConvertUtils.register(new SQLDateConverter(null), java.util.Date.class);
//            ConvertUtils.register(new SQLDateConverter(null), java.sql.Date.class);

//               ConvertUtils.register(new UserStringConverter(null) , String.class);
//               ConvertUtils.register(new DoubleConverter(null) , Double.class);
 //              ConvertUtils.register(new IntegerConverter(null), Integer.class);
               //usado para repositorio Ventas
//               ConvertUtils.register(new SQLTimestampConverter(), Timestamp.class);
               //usado para Comision, atributo codigoImportePlus
//               ConvertUtils.register(new LongConverter(null), Long.class);


            String fecha = "26-03-1970";

//            Converter converter = new SQLDateConverter(null);
//            Object dateFecha = converter.convert(java.sql.Date.class,fecha);


            //Object dateFecha = ConvertUtils.convert(fecha,java.sql.Date.class);

            UserVO userVO = new UserVO();
            userVO.setFormatDate("dd/MM/yyyy");

            Object dateFecha = userVO.getConverterStringToDate().convert(java.sql.Date.class,fecha);

            System.out.println("fecha");

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testConvertUtil() {
        try {
            UserVO userVO = new UserVO();
            userVO.setFormatDate("dd/MM/yyyy");

            ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            tl.set(userVO);


            String fecha = "26-03-1970";



            Object dateFecha = ConvertUtil.convertToDate(fecha);

            System.out.println("fecha");

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testConvertUtilLong() {
        try {
            UserVO userVO = new UserVO();
            userVO.setFormatDate("dd/MM/yyyy");

            ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            tl.set(userVO);


            String fecha = "26/03/1970";



            Timestamp time = ConvertUtil.convertToTimeStamp(fecha);

            System.out.println("fecha " + time.getTime());

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testConvertUtilStringLongString() {
        try {
            UserVO userVO = new UserVO();
            userVO.setFormatDate("dd/MM/yyyy");

            ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            tl.set(userVO);


            String fecha = "7268400000";
            Long longFecha = new Long(fecha);



            String time = ConvertUtil.convertToString(longFecha);

            System.out.println("fecha " + time);

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
