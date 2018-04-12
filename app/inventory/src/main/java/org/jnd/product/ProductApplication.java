package org.jnd.product;


import io.jaegertracing.Tracer.Builder;
import io.jaegertracing.metrics.Metrics;
import io.jaegertracing.metrics.NullStatsReporter;
import io.jaegertracing.metrics.StatsFactoryImpl;
import io.jaegertracing.propagation.B3TextMapCodec;
import io.jaegertracing.reporters.RemoteReporter;
import io.jaegertracing.reporters.Reporter;
import io.jaegertracing.samplers.ConstSampler;
import io.jaegertracing.senders.HttpSender;
import io.opentracing.propagation.Format;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan
@SpringBootApplication(scanBasePackages={"org.jnd"})
@RestController
@Configuration
@EnableAutoConfiguration
public class ProductApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProductApplication.class);
        app.run(ProductApplication.class, args);
    }

    @Bean
    @SuppressWarnings( "deprecation" )
    public io.opentracing.Tracer jaegerTracer() {
        Reporter reporter = new RemoteReporter.Builder().withFlushInterval(10)
                .withMaxQueueSize(65000)
                .withSender(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"))
                .withMetrics(new Metrics(new StatsFactoryImpl(new NullStatsReporter())))
                .build();


        Builder builder = new Builder("spring-boot")
                .withReporter(reporter)
                .withSampler(new ConstSampler(true))
                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());

//        Builder builder = new Builder("spring-boot",
//                new RemoteReporter(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"), 10,
//                        65000, new Metrics(new StatsFactoryImpl(new NullStatsReporter()))),
//                new ConstSampler(true))
//                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
//                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());

        return builder.build();
    }
}
