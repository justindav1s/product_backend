package org.jnd.microservices.grafeas;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.ApiException;
import io.swagger.client.api.GrafeasV1Beta1Api;
import io.swagger.client.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.jnd.microservices.grafeas.model.Project;
import org.jnd.microservices.grafeas.proxies.GrafeasProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GrafeasApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class GrafeasApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(GrafeasApplicationTests.class);

	@Value("${grafeas.host:not_found}")
	private String grafeas = null;

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void ProjectCreate200()
			throws Exception {

		String tstProjName = "project_"+randomString();


		MvcResult result = mvc.perform(post("/project/create")
				.content("{\"name\":\"projects/"+tstProjName+"\"}\"")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();


		String json = result.getResponse().getContentAsString();
		log.debug("result : " + json);
	}


	@Test
	public void ProjectCreateAddNote200()
			throws Exception {

		String tstProjName = "project_"+randomString();


		MvcResult result = mvc.perform(post("/project/create")
				.content("{\"name\":\"projects/"+tstProjName+"\"}\"")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();


		String json = result.getResponse().getContentAsString();
		log.info("Project Create result : " + json);
		log.info("Grafeas Server : " + grafeas);
		GrafeasV1Beta1Api apiInstance = new GrafeasV1Beta1Api();
		apiInstance.getApiClient().setBasePath(grafeas);

		String notename = "projects/"+tstProjName+"/notes/build_"+randomString();

		//Build
		V1beta1Note body = new V1beta1Note(); // ApiNote |
		body.setName(notename);
		body.setShortDescription("This is a build note  for project "+tstProjName);
		body.setLongDescription("Long description for build note for project "+tstProjName);
		body.createTime(OffsetDateTime.now());

		body.setKind(V1beta1NoteKind.BUILD);
		BuildBuild bb = new BuildBuild();
		bb.builderVersion("1.0");

		BuildBuildSignature bbs = new BuildBuildSignature();
		bbs.setKeyId("my_key");
		bbs.setKeyType(BuildSignatureKeyType.PKIX_PEM);
		bbs.setPublicKey("weqrewqrewqerwterwtr");
		byte[] bytes = {'s'};
		//bbs.setSignature(bytes);

		bb.setSignature(bbs);

		body.setBuild(bb);

		log.debug("body : "+body);
		try {
			V1beta1Note n = apiInstance.createNote(tstProjName, body);
			System.out.println(n);
		} catch (ApiException e) {
			System.err.println("Exception when calling GrafeasApi#createNote");
			e.printStackTrace();
		}

		V1beta1Note response = apiInstance.getNote(notename);
		log.debug("short description : "+response.getShortDescription());

		body = new V1beta1Note();
		notename = "projects/"+tstProjName+"/notes/deployable_"+randomString();
		body.setName(notename);
		body.setKind(V1beta1NoteKind.DEPLOYMENT);
		body.setShortDescription("This is a deployable note for project "+tstProjName);
		body.setLongDescription("Long description for deployable note for project "+tstProjName);
		body.createTime(OffsetDateTime.now());
		DeploymentDeployable dd = new DeploymentDeployable();
		dd.setResourceUri(Collections.singletonList("Docker Image Location"));
		body.deployable(dd);

		log.debug("body : "+body);
		try {
			V1beta1Note n = apiInstance.createNote(tstProjName, body);
			System.out.println(n);
		} catch (ApiException e) {
			System.err.println("Exception when calling GrafeasApi#createNote");
			e.printStackTrace();
		}

		response = apiInstance.getNote(notename);
		log.debug("short description : "+response.getShortDescription());

		body = new V1beta1Note();
		notename = "projects/"+tstProjName+"/notes/attestation_"+randomString();
		body.setName(notename);
		body.setKind(V1beta1NoteKind.ATTESTATION);
		body.setShortDescription("This is a attestation note for project "+tstProjName);
		body.setLongDescription("Long description for attestation note for project "+tstProjName);
		body.createTime(OffsetDateTime.now());
		AttestationAttestation aa = new AttestationAttestation();
		AttestationPgpSignedAttestation apas = new AttestationPgpSignedAttestation();
		PgpSignedAttestationContentType psact = PgpSignedAttestationContentType.SIMPLE_SIGNING_JSON;
		apas.setContentType(psact);
		apas.setPgpKeyId("my-pgpKey");
		apas.setSignature("sha256:weqertrewterwterwterterwterwt");
		AttestationAuthority aauth = new AttestationAuthority();

		log.debug("body : "+body);
		try {
			V1beta1Note n = apiInstance.createNote(tstProjName, body);
			System.out.println(n);
		} catch (ApiException e) {
			System.err.println("Exception when calling GrafeasApi#createNote");
			e.printStackTrace();
		}

//		body = new V1beta1Note();
//		notename = "projects/"+tstProjName+"/notes/deployment_"+randomString();
//		body.setName(notename);
//		body.setShortDescription("This is a deployment note  for project "+tstProjName);
//		body.setLongDescription("Long description for deployment note for project "+tstProjName);
//		body.createTime(OffsetDateTime.now());
//
//		body.setKind(V1beta1NoteKind.DEPLOYMENT);
//		DeploymentDeployment dd = new DeploymentDeployment();
//		dd.deployTime(OffsetDateTime.now());
//		dd.setUserEmail("email@email.com");
//		dd.setConfig("This is config information");
//		dd.setPlatform(DeploymentPlatform.fromValue("OSMO-DEV"));
//		dd.setAddress("Service Endpoint ?");
//		dd.setResourceUri(Collections.singletonList("Git URL ?"));
//
//		log.debug("body : "+body);
//		try {
//			V1beta1Note n = apiInstance.createNote(tstProjName, body);
//			System.out.println(n);
//		} catch (ApiException e) {
//			System.err.println("Exception when calling GrafeasApi#createNote");
//			e.printStackTrace();
//		}

		response = apiInstance.getNote(notename);
		log.debug("short description : "+response.getShortDescription());
	}


	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
}
