package mx.com.aon.catbo.model;
import net.sf.json.JSONObject;

public class ExtJSLabelVO{
		private String html;
		//private String colspan;

		public String getHtml() {
			return html;
		}

		public void setHtml(String html) {
			this.html = html;
		}
		
		public String toString () {
			String jsonObject = JSONObject.fromObject(this).toString();
			
			return jsonObject;
		}

/*		public String getColspan() {
			return colspan;
		}

		public void setColspan(String colspan) {
			this.colspan = colspan;
		}
*/
}
