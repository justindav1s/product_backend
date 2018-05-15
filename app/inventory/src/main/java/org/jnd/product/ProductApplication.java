package org.jnd.product;


//import brave.Tracing;
//import brave.opentracing.BraveTracer;
//import brave.sampler.Sampler;
//import io.jaegertracing.Tracer.Builder;
//import io.jaegertracing.metrics.*;
//import io.jaegertracing.propagation.B3TextMapCodec;
//import io.jaegertracing.reporters.RemoteReporter;
//import io.jaegertracing.reporters.Reporter;
//import io.jaegertracing.samplers.ConstSampler;
//import io.jaegertracing.senders.HttpSender;
//import io.opentracing.propagation.Format;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
//import zipkin.Span;
//import zipkin.reporter.AsyncReporter;
//import zipkin.reporter.Encoding;
//import zipkin.reporter.okhttp3.OkHttpSender;


@ComponentScan
@SpringBootApplication(scanBasePackages={"org.jnd"})
@RestController
@Configuration
@EnableAutoConfiguration
public class ProductApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

//    @Bean
//    public io.opentracing.Tracer jaegerTracer() {
//
//
//        Builder builder = new Builder("inventory",
//                new RemoteReporter(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"), 10,
//                        65000, new Metrics(new StatsFactoryImpl(new NullStatsReporter()))),
//                new ConstSampler(true))
//                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
//                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());
//        return builder.build();
//
////        Reporter reporter = new RemoteReporter.Builder().withFlushInterval(10)
////                .withMaxQueueSize(65000)
////                .withSender(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"))
////                .withMetrics(new Metrics(new NoopMetricsFactory()))
////                .build();
////
////        Builder builder = new Builder("inventory")
////                .withReporter(reporter)
////                .withSampler(new ConstSampler(true))
////                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
////                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());
////
////        return builder.build();
//
//    }

//    @Bean
//    public io.opentracing.Tracer zipkinTracer() {
//        OkHttpSender okHttpSender = OkHttpSender.create("http://zipkin.istio-system.svc:9411/api/v1/spans");
//        AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
//        Tracing braveTracer = Tracing.newBuilder().localServiceName("inventory").reporter(reporter).build();
//        return BraveTracer.create(braveTracer);
//    }

//    @Bean
//    public io.opentracing.Tracer zipkinTracer() {
//        OkHttpSender okHttpSender = OkHttpSender.builder()
//                .encoding(Encoding.JSON)
//                .endpoint("http://zipkin.istio-system.svc:9411/api/v1/spans")
//                .build();
//        AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
//        Tracing braveTracer = Tracing.newBuilder()
//                .localServiceName("spring-boot")
//                .reporter(reporter)
//                .traceId128Bit(true)
//                .sampler(Sampler.ALWAYS_SAMPLE)
//                .build();
//        return BraveTracer.create(braveTracer);
//    }
}
