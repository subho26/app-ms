package net.trajano.ms.engine.sample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import net.trajano.ms.engine.ManifestHandler;
import net.trajano.ms.engine.SpringJaxRsHandler;
import net.trajano.ms.engine.SwaggerHandler;

public class Main extends AbstractVerticle {

    public static void main(final String[] args) throws Exception {

        final VertxOptions vertOptions = new VertxOptions();
        vertOptions.setWarningExceptionTime(1);
        vertOptions.setWorkerPoolSize(50);

        final Vertx vertx = Vertx.vertx(vertOptions);
        final DeploymentOptions options = new DeploymentOptions();
        vertx.deployVerticle(new Main(), options);
    }

    private SpringJaxRsHandler requestHandler;

    @Override
    public void start() throws Exception {

        final Router router = Router.router(vertx);

        final HttpServerOptions httpServerOptions = new HttpServerOptions();
        httpServerOptions.setPort(8900);

        final HttpServer http = vertx.createHttpServer(httpServerOptions);

        SwaggerHandler.registerToRouter(router, MyApp.class);
        requestHandler = SpringJaxRsHandler.registerToRouter(router, MyApp.class);
        ManifestHandler.registerToRouter(router);

        http.requestHandler(req -> router.accept(req)).listen(res -> {
            if (res.failed()) {
                res.cause().printStackTrace();
                vertx.close();
            }
        });
    }

    @Override
    public void stop() throws Exception {

        requestHandler.close();
    }

}
