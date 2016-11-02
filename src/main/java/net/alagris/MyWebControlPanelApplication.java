package net.alagris;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
@ImportResource("classpath:/Beans.xml")
public class MyWebControlPanelApplication {

    @Bean
    public ViewResolver viewResolver() {
	ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	templateResolver.setTemplateMode("XHTML");
	templateResolver.setPrefix("classpath:/src/main/resources/templates/");
	templateResolver.setSuffix(".html");
	SpringTemplateEngine engine = new SpringTemplateEngine();
	engine.setTemplateResolver(templateResolver);

	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	viewResolver.setTemplateEngine(engine);
	return viewResolver;
    }

    public static void main(String[] args) {
	SpringApplication.run(MyWebControlPanelApplication.class, args);
    }

    

}
