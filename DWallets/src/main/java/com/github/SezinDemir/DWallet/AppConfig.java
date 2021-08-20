 package com.github.SezinDemir.DWallet;
 import com.datastax.oss.driver.api.core.CqlSession;
 import com.github.SezinDemir.DWallet.service.CustomerService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.ComponentScan;
 import org.springframework.context.annotation.Configuration;
 import reactor.netty.DisposableServer;
 import reactor.netty.http.server.HttpServer;
 import java.net.URISyntaxException;
 import java.nio.file.Path;
 import java.nio.file.Paths;

 @Configuration
 @ComponentScan
 public class AppConfig {

 @Autowired
 CustomerService customerService;

 @Bean
 public CqlSession session() {
        return CqlSession.builder().build();
    }

 @Bean
 public DisposableServer server() throws URISyntaxException {
      Path newaccountHTML = Paths.get(App.class.getResource("/newaccount.html").toURI());
      Path errorHTML = Paths.get(App.class.getResource("/error.html").toURI());

      return HttpServer.create()
            .port(8080)
            .route(routes ->
                  routes.get("/customers", (request, response) ->
                        response.send(customerService.getAll()
                            .map(App::toByteBuf)
                            .log("http-server")))

                        .post("/customers", (request, response) ->
                            response.send(request.receive().asString()
                            .map(App::parseCustomer)
                            .map(customerService::create)
                            .map(App::toByteBuf)
                            .log("http-server")))

                        .get("/customers/{param}", (request, response) ->
                             response.send(customerService.get(request.param("param"))
                             .map(App::toByteBuf)
                             .log("http-server")))

                        .get("/customers/delete/{id}", (request, response) ->
                             response.send(
                             customerService.deleteCustomer(request.param("id"))
                             .map(App::toByteBuf)
                             .log("http-server")))

                        .get("/newaccount", (request, response) ->
                               response.sendFile(newaccountHTML))

                        .get("/error", (request, response) ->
                              response.status(404).addHeader("Message", "Error")
                             .sendFile(errorHTML))
                )
                .bindNow();
        }
   }



