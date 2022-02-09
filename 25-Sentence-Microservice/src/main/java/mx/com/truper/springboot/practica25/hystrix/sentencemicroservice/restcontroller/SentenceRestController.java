package mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.restcontroller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.MyListener;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.IAdjectiveServiceClient;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.IArticleServiceClient;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.INounServiceClient;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.ISubjectServiceClient;
import mx.com.truper.springboot.practica25.hystrix.sentencemicroservice.client.IVerbServiceClient;

@Slf4j
@RestController
public class SentenceRestController {

	@Autowired
	private ISubjectServiceClient subjectServiceClient;

	@Autowired
	private IVerbServiceClient verbServiceClient;

	@Autowired
	private IArticleServiceClient articleServiceClient;

	@Autowired
	private IAdjectiveServiceClient adjectiveServiceClient;

	@Autowired
	private INounServiceClient nounServiceClient;

	@Autowired
	private Environment env;

	@GetMapping("/sentence")
	public Map<String, Object> sentence() {

		Map<String, Object> map = new LinkedHashMap<>();

		map.put("sentence", this.buildSentence());
		map.put("from", "http://" + env.getProperty("spring.application.name") + ":" + MyListener.APPLICATION_PORT);

		log.info("sending sentence: {}", map.get("sentence"));

		return map;
	}

	private String buildSentence() {
		StringBuilder sb = new StringBuilder();

		log.info("calling subject");

		sb.append(subjectServiceClient.getSubject());
		sb.append(" ");

		log.info("calling verb");

		sb.append(verbServiceClient.getVerb());
		sb.append(" ");

		log.info("calling article");

		sb.append(articleServiceClient.getArticle());
		sb.append(" ");

		log.info("calling adjective");

		sb.append(adjectiveServiceClient.getAdjective());
		sb.append(" ");

		log.info("calling noun");

		sb.append(nounServiceClient.getNoun());

		return sb.toString();
	}
}
