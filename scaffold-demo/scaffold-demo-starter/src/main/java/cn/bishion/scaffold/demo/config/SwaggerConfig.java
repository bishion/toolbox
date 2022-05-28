package cn.bishion.scaffold.demo.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author SwaggerConfiguration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    /**
     * SwaggerConfiguration
     *
     * @param openApiExtensionResolver
     */
    @Autowired
    public SwaggerConfig(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    /**
     * defaultApi2
     *
     * @return
     */
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String groupName = "1.0.0版本";
        return new Docket(DocumentationType.SWAGGER_2).host("https://www.baidu.com").apiInfo(apiInfo())
                .groupName(groupName).select().apis(RequestHandlerSelectors.basePackage("cn.bishion.scaffold"))
                .paths(PathSelectors.any()).build().extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    /**
     * apiInfo
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("scaffold RESTFUL APIs")
                .description("#swagger-bootstrap-ui-demo RESTFUL APIs").termsOfServiceUrl("http://www.baidu.com/")
                .contact(new Contact("百度", "https://www.baidu.com", "xx@qq.com")).version("1.0.0").build();
    }

}
