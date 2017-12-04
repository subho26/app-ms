package net.trajano.ms.engine.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.impl.headers.VertxHttpHeaders;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import net.trajano.ms.engine.SpringJaxRsHandler;
import net.trajano.ms.engine.sample.MyApp;

@RunWith(VertxUnitRunner.class)
public class SpringJaxRsHandlerTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    @Test
    public void test400(final TestContext testContext) throws Exception {

        final Router router = Router.router(rule.vertx());
        SpringJaxRsHandler.registerToRouter(router, MyApp.class);

        final HttpServerRequest serverRequest = mock(HttpServerRequest.class);
        when(serverRequest.absoluteURI()).thenReturn("http://test.trajano.net/api/hello/400");
        when(serverRequest.path()).thenReturn("/api/hello/400");
        when(serverRequest.uri()).thenReturn("/api/hello/400");
        when(serverRequest.isEnded()).thenReturn(true);
        when(serverRequest.method()).thenReturn(HttpMethod.GET);

        final HttpServerResponse response = mock(HttpServerResponse.class);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        when(response.headers()).thenReturn(new VertxHttpHeaders());

        final Async async = testContext.async();
        when(response.setStatusCode(Matchers.any(Integer.class))).then(invocation -> {

            try {
                return response;
            } finally {
                async.complete();
            }
        });
        when(serverRequest.response()).thenReturn(response);

        router.accept(serverRequest);
        async.await();

        verify(response, times(1)).setStatusCode(400);
    }

    @Test
    public void test404(final TestContext testContext) throws Exception {

        final Router router = Router.router(rule.vertx());
        SpringJaxRsHandler.registerToRouter(router, MyApp.class);

        final HttpServerRequest serverRequest = mock(HttpServerRequest.class);
        when(serverRequest.absoluteURI()).thenReturn("http://test.trajano.net/api/nothello");
        when(serverRequest.path()).thenReturn("/api/nothello");
        when(serverRequest.uri()).thenReturn("/api/nothello");
        when(serverRequest.isEnded()).thenReturn(true);
        when(serverRequest.method()).thenReturn(HttpMethod.GET);

        final HttpServerResponse response = mock(HttpServerResponse.class);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        when(response.headers()).thenReturn(new VertxHttpHeaders());

        final Async async = testContext.async();
        Mockito.doAnswer(invocation -> {

            async.complete();
            return null;
        }).when(response).end("Not Found");
        when(serverRequest.response()).thenReturn(response);

        router.accept(serverRequest);
        async.await();

        verify(response, times(1)).setStatusCode(404);
        verify(response, times(1)).end("Not Found");
    }

    @Test
    public void test404Internal(final TestContext testContext) throws Exception {

        final Router router = Router.router(rule.vertx());
        SpringJaxRsHandler.registerToRouter(router, MyApp.class);

        final HttpServerRequest serverRequest = mock(HttpServerRequest.class);
        when(serverRequest.absoluteURI()).thenReturn("http://test.trajano.net/api/hello/404");
        when(serverRequest.path()).thenReturn("/api/hello/404");
        when(serverRequest.uri()).thenReturn("/api/hello/404");
        when(serverRequest.isEnded()).thenReturn(true);
        when(serverRequest.method()).thenReturn(HttpMethod.GET);

        final HttpServerResponse response = mock(HttpServerResponse.class);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        when(response.headers()).thenReturn(new VertxHttpHeaders());

        final Async async = testContext.async();
        when(response.setStatusCode(Matchers.any(Integer.class))).then(invocation -> {

            try {
                return response;
            } finally {
                async.complete();
            }
        });
        when(serverRequest.response()).thenReturn(response);

        router.accept(serverRequest);
        async.await();

        verify(response, times(1)).setStatusCode(404);
    }

    @Test
    public void test500(final TestContext testContext) throws Exception {

        final Router router = Router.router(rule.vertx());
        SpringJaxRsHandler.registerToRouter(router, MyApp.class);

        final HttpServerRequest serverRequest = mock(HttpServerRequest.class);
        when(serverRequest.absoluteURI()).thenReturn("http://test.trajano.net/api/hello/cough");
        when(serverRequest.path()).thenReturn("/api/hello/cough");
        when(serverRequest.uri()).thenReturn("/api/hello/cough");
        when(serverRequest.isEnded()).thenReturn(true);
        when(serverRequest.method()).thenReturn(HttpMethod.GET);

        final HttpServerResponse response = mock(HttpServerResponse.class);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        when(response.headers()).thenReturn(new VertxHttpHeaders());

        final Async async = testContext.async();
        when(response.write(Matchers.any(Buffer.class))).then(invocation -> {

            try {
                return response;
            } finally {
                async.complete();
            }
        });
        when(serverRequest.response()).thenReturn(response);

        router.accept(serverRequest);
        async.await();

        final ArgumentCaptor<Buffer> captor = ArgumentCaptor.forClass(Buffer.class);
        verify(response, times(1)).setStatusCode(500);
        verify(response, atLeastOnce()).write(captor.capture());
        final String errorMessage = String.join("", captor.getAllValues().stream().map(Buffer::toString).collect(Collectors.toList()));
        assertTrue(errorMessage.contains("server_error"));
    }

    @Test
    public void testHandler(final TestContext testContext) throws Exception {

        final Router router = Router.router(rule.vertx());
        SpringJaxRsHandler.registerToRouter(router, MyApp.class);

        final HttpServerRequest serverRequest = mock(HttpServerRequest.class);
        when(serverRequest.absoluteURI()).thenReturn("http://test.trajano.net/api/hello");
        when(serverRequest.path()).thenReturn("/api/hello");
        when(serverRequest.uri()).thenReturn("/api/hello");
        when(serverRequest.isEnded()).thenReturn(true);
        when(serverRequest.method()).thenReturn(HttpMethod.GET);

        final HttpServerResponse response = mock(HttpServerResponse.class);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        when(response.headers()).thenReturn(new VertxHttpHeaders());

        final Async async = testContext.async();
        when(response.write(Matchers.any(Buffer.class))).then(invocation -> {

            try {
                return response;
            } finally {
                async.complete();
            }
        });
        when(serverRequest.response()).thenReturn(response);

        router.accept(serverRequest);
        async.await();

        final ArgumentCaptor<Buffer> captor = ArgumentCaptor.forClass(Buffer.class);
        verify(response, times(1)).setStatusCode(200);
        verify(response, times(1)).setChunked(true);
        verify(response, times(1)).write(captor.capture());
        assertTrue(captor.getValue().toString().startsWith("Hello"));
    }
}