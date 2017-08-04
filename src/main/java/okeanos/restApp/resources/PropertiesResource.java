package okeanos.restApp.resources;

import static spark.Spark.get;

import okeanos.util.AppProperties;

public class PropertiesResource extends AbstractResource {

	protected static String ressourcePath = "/properties.js";

	public PropertiesResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			StringBuilder sb = new StringBuilder();
			sb.append("'use strict';").append(System.getProperty("line.separator"));
			sb.append("var okeanoAppUrl = '").append(AppProperties.getProperties().frontRestAcces).append(":")
					.append(AppProperties.getProperties().restHostPort).append(AppProperties.API_CONTEXT).append("';")
					.append(System.getProperty("line.separator"));
			sb.append("var modeDebug = ").append(AppProperties.getProperties().frontModeDebug).append(";");
			return sb.toString();
		});
	}

}