package com.tcra.hrms.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.tcra.hrms" })
public class WebConfig implements WebMvcConfigurer {
	private static final Logger logger = Logger.getLogger(WebConfig.class); // log4j

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp().prefix("/WEB-INF/views/").suffix(".jsp");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
	}

	// equivalents for <mvc:resources/> tags
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("WebMvcConfigurer - addResourceHandlers() loaded...");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
				.addResourceLocations("/WEB-INF/classes/");
	}

	// equivalent for <mvc:default-servlet-handler/> tag
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(false)
				.useJaf(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("csv", new MediaType("text", "csv"));
	}

	/*
	 * @Override public void configureMessageConverters(
	 * List<HttpMessageConverter<?>> converters) { // put the jackson converter to
	 * the front of the list so that application/json content-type strings will be
	 * treated as JSON converters.add(new MappingJackson2HttpMessageConverter()); //
	 * and probably needs a string converter too for text/plain content-type strings
	 * to be properly handled converters.add(new StringHttpMessageConverter()); }
	 */

	/*
	 * @Override public void
	 * configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	 * //converters.add(createXmlHttpMessageConverter()); converters.add(new
	 * MappingJackson2HttpMessageConverter()); }
	 */

	/*
	 * private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
	 * MarshallingHttpMessageConverter xmlConverter = new
	 * MarshallingHttpMessageConverter();
	 * 
	 * XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
	 * xmlConverter.setMarshaller(xstreamMarshaller);
	 * xmlConverter.setUnmarshaller(xstreamMarshaller);
	 * 
	 * return xmlConverter; }
	 */

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf8");
		// multipartResolver.setMaxUploadSize(100000);
		return multipartResolver;
	}
	
	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * System.out.
	 * println("WebMvcConfigurer - addResourceHandlers() function get loaded...");
	 * 
	 * registry.addResourceHandler("/resources/**")
	 * .addResourceLocations("/resources/")
	 * .addResourceLocations("classpath:/resources/");
	 * //.addResourceLocations("classpath:/META-INF/resources/")
	 * //.addResourceLocations("classpath:/public/")
	 * //.addResourceLocations("classpath:/static/");
	 * 
	 * super.addResourceHandlers(registry); }
	 * 
	 * //equivalent for <mvc:default-servlet-handler/> tag
	 * 
	 * @Override public void
	 * configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	 * configurer.enable(); }
	 * 
	 * 
	 * @Bean(name="restTemplate") public RestTemplate restTemplate() { RestTemplate
	 * restTemplate = new RestTemplate(); return restTemplate; }
	 * 
	 */
	/*
	 * @Bean(name="restTemplate") public RestTemplate restTemplate() { RestTemplate
	 * restTemplate = new RestTemplate();
	 * 
	 * List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
	 * MappingJackson2HttpMessageConverter converter = new
	 * MappingJackson2HttpMessageConverter();
	 * converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
	 * messageConverters.add(converter);
	 * restTemplate.setMessageConverters(messageConverters);
	 * 
	 * return restTemplate; }
	 */

	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @Bean public RestTemplate restTemplate() {
	 * 
	 * SimpleClientHttpRequestFactory factory = new
	 * SimpleClientHttpRequestFactory(); factory.setConnectTimeout(3000);
	 * factory.setReadTimeout(3000);
	 * 
	 * RestTemplate template = new RestTemplate(); //RestTemplate template = new
	 * RestTemplate(factory);
	 * 
	 * // converters List<HttpMessageConverter<?>> messageConverters = new
	 * ArrayList(); messageConverters.add(new
	 * MappingJackson2XmlHttpMessageConverter()); messageConverters.add(new
	 * MappingJackson2HttpMessageConverter()); messageConverters.add(new
	 * GsonHttpMessageConverter());
	 * 
	 * // assign template.setMessageConverters(messageConverters);
	 * 
	 * return template; }
	 */

}
