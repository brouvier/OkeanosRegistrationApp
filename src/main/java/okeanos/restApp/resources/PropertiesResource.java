package okeanos.restApp.resources;

import static spark.Spark.get;

import okeanos.util.AppProperties;

public class PropertiesResource extends AbstractResource {

	protected static String ressourcePath = "/properties.js";

	public PropertiesResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			return gson.toJson(new PropertiesBean());
		});
	}

	/**
	 * Structure de données pour les échanges avec le front
	 */
	private class PropertiesBean {
		@SuppressWarnings("unused")
		public String okeanoAppUrl;
		@SuppressWarnings("unused")
		public Boolean modeDebug;

		public PropertiesBean() {
			this.okeanoAppUrl = AppProperties.getProperties().frontRestAcces + ":"
					+ AppProperties.getProperties().restHostPort + AppProperties.API_CONTEXT;
			this.modeDebug = "true".equals(AppProperties.getProperties().frontModeDebug);
		}
	}

}