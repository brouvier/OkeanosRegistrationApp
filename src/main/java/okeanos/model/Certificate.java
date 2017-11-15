package okeanos.model;

import okeanos.util.AppProperties;

public class Certificate {

	private String body;
	private String footer;

	public Certificate(String template, String adherentNom, String saisonLabel, String adhesionMontant,
			String adhesionDate) {
		super();
		this.footer = template.substring(template.indexOf("<footer>") + 8, template.indexOf("</footer>"));
		this.body = template.substring(0, template.indexOf("<footer>"));

		replaceKey("{logoPath}", AppProperties.TEMPLATE_LOGO);
		replaceKey("{signatuePath}", AppProperties.TEMPLATE_SIGN);

		replaceKey("{adherentNom}", adherentNom);
		replaceKey("{saisonLabel}", saisonLabel);
		replaceKey("{adhesionMontant}", adhesionMontant);
		replaceKey("{adhesionDate}", adhesionDate);
	}

	public String getBody() {
		return body;
	}

	public String getFooter() {
		return footer;
	}

	protected void replaceKey(String key, String value) {
		this.body = this.body.replace(key, value);
	}

}
