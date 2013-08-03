package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener grupo de personas
 * 
 * @param cdGrupo               codigo de grupo persona
 * @param cdAgrGrupo            codigo agrupacion grupo persona  
 * @param cdAgrupa              codigo agrupacion
 * @param cdGrupoPer            codigo grupo personas    
 *
 */

public class GrupoPersonaVO {

		private String cdGrupo;
		private String cdAgrGrupo;
		private String cdAgrupa;
		private String cdGrupoPer;
		private String cdPolMtra;
		private String nmPoliEx;
		private String feInicio;
		private String feFin;
		
		

		public String getCdGrupo() {
			return cdGrupo;
		}

		public void setCdGrupo(String cdGrupo) {
			this.cdGrupo = cdGrupo;
		}

		public String getCdAgrGrupo() {
			return cdAgrGrupo;
		}

		public void setCdAgrGrupo(String cdAgrGrupo) {
			this.cdAgrGrupo = cdAgrGrupo;
		}

		public String getCdAgrupa() {
			return cdAgrupa;
		}

		public void setCdAgrupa(String cdAgrupa) {
			this.cdAgrupa = cdAgrupa;
		}

		public String getCdGrupoPer() {
			return cdGrupoPer;
		}

		public void setCdGrupoPer(String cdGrupoPer) {
			this.cdGrupoPer = cdGrupoPer;
		}

		public String getCdPolMtra() {
			return cdPolMtra;
		}

		public void setCdPolMtra(String cdPolMtra) {
			this.cdPolMtra = cdPolMtra;
		}

		public String getNmPoliEx() {
			return nmPoliEx;
		}

		public void setNmPoliEx(String nmPoliEx) {
			this.nmPoliEx = nmPoliEx;
		}

		public String getFeInicio() {
			return feInicio;
		}

		public void setFeInicio(String feInicio) {
			this.feInicio = feInicio;
		}

		public String getFeFin() {
			return feFin;
		}

		public void setFeFin(String feFin) {
			this.feFin = feFin;
		} 

		
}



