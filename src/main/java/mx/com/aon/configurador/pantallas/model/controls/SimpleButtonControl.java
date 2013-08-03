package mx.com.aon.configurador.pantallas.model.controls;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Deprecated
public class SimpleButtonControl extends AbstractButtonControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7480135688991079999L;
	
	public SimpleButtonControl() {
		xtype = "button";
	}
	
	@Override
	public String toString() {
		
		JSONObject json = JSONObject.fromObject(super.toString());	
		
		//TODO: VERIFICAR VALIDACION DE LA FUNCION.
		if(  !JSONUtils.isFunction(this.handler) ){
			json.discard("handler");			
		}		
		return  json.toString();		
	}
	
	
	public static void main(String[] args) {
		SimpleButtonControl b1 = new SimpleButtonControl();
		b1.setText("cotizar");
		b1.setId("1");
		JSONFunction f = new JSONFunction("hola");
		b1.setHandler(f);
		
		System.out.print("botton 1 es " + b1);
		
	}
	

}
