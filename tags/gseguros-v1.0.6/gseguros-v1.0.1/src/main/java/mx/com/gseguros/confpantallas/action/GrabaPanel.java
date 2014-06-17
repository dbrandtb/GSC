package mx.com.gseguros.confpantallas.action;

import mx.com.gseguros.confpantallas.delegate.AdminPanelesDelegate;

public class GrabaPanel {
	private String nombrepanel;
	private String tarea;
	private String regreso;
	private String json;
	
	 public String execute() {
		 System.out.println("Ya ingrese a mi action :::::");
		 AdminPanelesDelegate adm = new AdminPanelesDelegate();
		 if(getTarea().equals("existe")){
			 setRegreso(adm.ExistePanel(getNombrepanel()));
		 }else if(getTarea().equals("graba")){
			 setRegreso(adm.SetPanel(getNombrepanel(), getJson()));
			 System.out.println(getJson());
			 
		 }
		 
		 
		 return "SUCCESS";
	 }

	public String getNombrepanel() {
		return nombrepanel;
	}

	public void setNombrepanel(String nombrepanel) {
		this.nombrepanel = nombrepanel;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getRegreso() {
		return regreso;
	}

	public void setRegreso(String regreso) {
		this.regreso = regreso;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
