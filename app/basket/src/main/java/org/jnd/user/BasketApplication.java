package org.jnd.user;

//import com.uber.jaeger.metrics.Metrics;
//import com.uber.jaeger.metrics.NullStatsReporter;
//import com.uber.jaeger.metrics.StatsFactoryImpl;
//import com.uber.jaeger.propagation.B3TextMapCodec;
//import com.uber.jaeger.reporters.RemoteReporter;
//import com.uber.jaeger.samplers.ConstSampler;
//import com.uber.jaeger.senders.HttpSender;
//import io.opentracing.propagation.Format;
//import com.uber.jaeger.Tracer.Builder;

import io.jaegertracing.Tracer.Builder;
import io.jaegertracing.metrics.*;
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

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class BasketApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BasketApplication.class, args);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }


    @Bean
    public io.opentracing.Tracer jaegerTracer() {
//        Builder builder = new Builder("basket",
//                new RemoteReporter(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"), 10,
//                        65000, new Metrics(new StatsFactoryImpl(new NullStatsReporter()))),
//                new ConstSampler(true))
//                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
//                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());
//        return builder.build();

        Reporter reporter = new RemoteReporter.Builder().withFlushInterval(10)
                .withMaxQueueSize(65000)
                .withSender(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"))
                .withMetrics(new Metrics(new NoopMetricsFactory()))
                .build();

        Builder builder = new Builder("basket")
                .withReporter(reporter)
                .withSampler(new ConstSampler(true))
                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());

        return builder.build();
    }
}
