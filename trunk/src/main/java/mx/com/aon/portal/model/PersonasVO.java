
/**
 * 
 */
package mx.com.aon.portal.model;

/**
 * Clase VO que contempla la estructura de datos usada en la pantalla de personas y
 * la de configurar estructura.
 * 
 * @param codigoPersona
 * @param nombre
 * @param cdTipPer
 * @param dsTipPer
 * @param dsElemen
 * @param cdRfc
 * @param nmNumNom
 */
public class PersonasVO {
        private String codigoPersona;
		private String nombre;
		private String cdTipPer;
		private String dsTipPer;
		private String dsElemen;
		private String cdRfc;
		private String nmNumNom;
		private String dsFisJur;
		private String otFisJur;
		
		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getCodigoPersona() {
			return codigoPersona;
		}

		public void setCodigoPersona(String codigoPersona) {
			this.codigoPersona = codigoPersona;
		}

		public String getCdTipPer() {
			return cdTipPer;
		}

		public void setCdTipPer(String cdTipPer) {
			this.cdTipPer = cdTipPer;
		}

		public String getDsTipPer() {
			return dsTipPer;
		}

		public void setDsTipPer(String dsTipPer) {
			this.dsTipPer = dsTipPer;
		}

		public String getDsElemen() {
			return dsElemen;
		}

		public void setDsElemen(String dsElemen) {
			this.dsElemen = dsElemen;
		}

		public String getCdRfc() {
			return cdRfc;
		}

		public void setCdRfc(String cdRfc) {
			this.cdRfc = cdRfc;
		}

		public String getNmNumNom() {
			return nmNumNom;
		}

		public void setNmNumNom(String nmNumNom) {
			this.nmNumNom = nmNumNom;
		}

		public String getDsFisJur() {
			return dsFisJur;
		}

		public void setDsFisJur(String dsFisJur) {
			this.dsFisJur = dsFisJur;
		}

		public String getOtFisJur() {
			return otFisJur;
		}

		public void setOtFisJur(String otFisJur) {
			this.otFisJur = otFisJur;
		}

}