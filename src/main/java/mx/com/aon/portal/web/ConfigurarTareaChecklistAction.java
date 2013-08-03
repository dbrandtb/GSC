package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.TareaChecklistVO;
import mx.com.aon.portal.service.EstructuraManagerTareasChecklist;
import org.apache.log4j.Logger;

import java.util.List;

/**
 *   Action que atiende las peticiones de la pantalla Configurar Tareas Checklist
 * 
 */

@SuppressWarnings("serial")
public class ConfigurarTareaChecklistAction extends ActionSupport {
		
		private String codSeccion;
		private String codTarea;
		private String seccion;
		private String tarea;
		private String estado;	
		private boolean success; 
		
		
		/**
		 * Logger de la clase para monitoreo y registro de comportamiento
		 */
		@SuppressWarnings("unused") 
		private static Logger logger = Logger.getLogger(ConfigurarTareaChecklistAction.class);
		
		/**
		 * Manager con implementacion de Endpoint para la consulta a BD
		 */
		private EstructuraManagerTareasChecklist estructuraManager;
		
		
		private List<TareaChecklistVO> mEstructuraList;
		/**
		 * Atributo agregado como parametro de la petición por struts que indica
		 * el inicio de el número de linea en cual iniciar  
		 */
		private String start;
		
		
		
		/**
		 * Atributo agregado como parametro de la petición por struts que indica la cantidad
		 * de registros a ser consultados 
		 */
		private String limit;
		

		public String getSeccion() {
			return seccion;
		}

		public void setSeccion(String seccion) {
			this.seccion = seccion;
		}

		public String getTarea() {
			return tarea;
		}

		public void setTarea(String tarea) {
			this.tarea = tarea;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}


		public boolean getSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public EstructuraManagerTareasChecklist getEstructuraManager() {
			return estructuraManager;
		}

		public void setEstructuraManager(EstructuraManagerTareasChecklist estructuraManager) {
			this.estructuraManager = estructuraManager;
		}

		public List<TareaChecklistVO> getMEstructuraList() {
			return mEstructuraList;
		}

		public void setMEstructuraList(List<TareaChecklistVO> estructuraList) {
			mEstructuraList = estructuraList;
		}

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getLimit() {
			return limit;
		}

		public void setLimit(String limit) {
			this.limit = limit;
		}



    public String getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(String codSeccion) {
        this.codSeccion = codSeccion;
    }

    public String getCodTarea() {
        return codTarea;
    }

    public void setCodTarea(String codTarea) {
        this.codTarea = codTarea;
    }
}
