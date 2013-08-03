package mx.com.aon.catbo.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WrapperInteger implements Serializable {
	

		/** La cadena contenida */
		private String someInteger;


    public String getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(String someInteger) {
        this.someInteger = someInteger;
    }
}


	
	


