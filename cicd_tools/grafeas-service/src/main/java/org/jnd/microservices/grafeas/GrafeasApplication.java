package org.jnd.microservices.grafeas;

import com.squareup.okhttp.OkHttpClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.GrafeasV1Beta1Api;
import io.swagger.client.model.V1beta1Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@RestController
public class GrafeasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrafeasApplication.class, args);
	}

	@Value("${grafeas.host:not_found}")
	private String grafeas = null;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "GRAFEAS";
	}

	@RequestMapping(value = "/go", method = RequestMethod.GET)
	public String go()	{
		OkHttpClient httpClient = new OkHttpClient();
		GrafeasV1Beta1Api apiInstance = new GrafeasV1Beta1Api();
		apiInstance.getApiClient().setBasePath(grafeas);
		String parent = "parent_example"; // String |
		V1beta1Note body = new V1beta1Note(); // ApiNote |
		try {
			V1beta1Note result = apiInstance.createNote(parent, body);
			System.out.println(result);
		} catch (ApiException e) {
			System.err.println("Exception when calling GrafeasApi#createNote");
			e.printStackTrace();
		}

	}
}
