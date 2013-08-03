package mx.com.aon.portal.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class WrapperResultados implements Serializable {
	
	 /** tipos de error */  
	 private static final String MSGTITLE_ERROR = "1";
	 private static final String MSGTITLE_INFO = "2";
	 private static final String MSGTITLE_INTERROG = "3";
	 
	 /** identificador del mensaje resultante de la operacion **/
	 private String msgId;
	 
	 /**
	  * @deprecated se quitara msg, el mensaje puede ser obtenido usando el msgId
	  */
	 private String msg;
	 
	 /** Tipo de mensaje o error, resultante de la operacion **/
	 private String msgTitle;

     private String msgText;

     /**En caso de estar paginado el resultado de la lista itemList, almacena el total de la lista original
	  sin paginar */
	 int notPagedTotalItems;

	 /** Lista de resultados de consultas o cualquier cursor de salida */
	 @SuppressWarnings("unchecked")
	 private List itemList;
	 
	 /** Mapa para colocar cualquier otro parametro de salida */
	 private Map<String,Object> itemMap; 


	 private static final long serialVersionUID = 1L;

     // utiilado en algunos plsql ej: validar_existe_tarea
     private String resultado;

//usado para P_OBTIENE_MONTOS_CARRITO
     @SuppressWarnings("unchecked")
	private ArrayList resultados;
     
     
//usado en CALCULA_DESCUENTO_CARRITO_COMPRAS
     
     private String descuento;
     private String subTotal;
 	 private String totalFn;
     
//usado en GUARDAR_EDITA_CONFIGURACION
 	 private String actPadre;
 	 private String actHijo;
 	 
//usado en OBTENER_PREGUNTA_ENCUESTA
     private String cdSecuenciaPro;
     
     /**
	  * Permite saber si el mensaje recibido fue de error
	  */
	 public boolean isMsgError(){
		 return this.msgTitle.equals(MSGTITLE_ERROR);
	 }
     /**
	  * Permite saber si el mensaje recibido fue de error
	  */
	 public boolean isMsgInformation(){
		 return this.msgTitle.equals(MSGTITLE_INFO);
	 }
     /**
	  * Permite saber si el mensaje recibido fue de error
	  */
	 public boolean isMsgInterrogation(){
		 return this.msgTitle.equals(MSGTITLE_INTERROG);
	 }	 
	 
	 /**
	  * @return the msgId
	  */
	 public String getMsgId() {
		 
	  if(StringUtils.isNotBlank(msgId)){
		   StringTokenizer tokens = new StringTokenizer(msgId);  
		   if(tokens.hasMoreTokens()){  
			   msgId = tokens.nextToken();  
		   }  
	  }
	  
	  return msgId;
	 }

	 /**
	  * @param msgId the msgId to set
	  */
	 public void setMsgId(String msgId) {
	  this.msgId = msgId;
	 }


	 /**
	  * @return the itemList
	  */
	 @SuppressWarnings("unchecked")
	public List getItemList() {
	  return itemList;
	 }

	 /**
	  * @param itemList the itemList to set
	  */
	 @SuppressWarnings("unchecked")
	public void setItemList(List itemList) {
	  this.itemList = itemList;
	 }

	 /**
	  * @return the itemMap
	  */
	 public Map<String, Object> getItemMap() {
	  return itemMap;
	 }

	 /**
	  * @param itemMap the itemMap to set
	  */
	 public void setItemMap(Map<String, Object> itemMap) {
	  this.itemMap = itemMap;
	 }

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getNotPagedTotalItems() {
		return notPagedTotalItems;
	}

	public void setNotPagedTotalItems(int notPagedTotalItems) {
		this.notPagedTotalItems = notPagedTotalItems;
	}


    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }


    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
	@SuppressWarnings("unchecked")
	public ArrayList getResultados() {
		return resultados;
	}
	@SuppressWarnings("unchecked")
	public void setResultados(ArrayList resultados) {
		this.resultados = resultados;
	}
	/**
	 * @return the descuento
	 */
	public String getDescuento() {
		return descuento;
	}
	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	/**
	 * @return the subTotal
	 */
	public String getSubTotal() {
		return subTotal;
	}
	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	/**
	 * @return the totalFn
	 */
	public String getTotalFn() {
		return totalFn;
	}
	/**
	 * @param totalFn the totalFn to set
	 */
	public void setTotalFn(String totalFn) {
		this.totalFn = totalFn;
	}
	public String getActPadre() {
		return actPadre;
	}
	public void setActPadre(String actPadre) {
		this.actPadre = actPadre;
	}
	public String getActHijo() {
		return actHijo;
	}
	public void setActHijo(String actHijo) {
		this.actHijo = actHijo;
	}
	public String getCdSecuenciaPro() {
		return cdSecuenciaPro;
	}
	public void setCdSecuenciaPro(String cdSecuenciaPro) {
		this.cdSecuenciaPro = cdSecuenciaPro;
	}
	
}
