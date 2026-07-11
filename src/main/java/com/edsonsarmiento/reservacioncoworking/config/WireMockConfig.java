package com.edsonsarmiento.reservacioncoworking.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WireMockConfig {

    private WireMockServer wireMockServer;

    @PostConstruct
    public void iniciarServidorMock(){
        //arrancamos servidor
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8081));
        wireMockServer.start();

        log.info("Servidor WireMock iniciado en 8081");

        configuracionEscenariosPrueba();

    }

    private void configuracionEscenariosPrueba(){


        //ESCENARIO 1: Pago Exitoso
        /**/
        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/api/pagos/validar/.*"))
            .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ \"status\": \"APPROVED\", \"mensaje\": \"Pago exitoso\" }")));


        /*
            ESCENARIO 2: Simular Inestabilidad / Lentitud extrema
            Spring Boot abortará a los 2 segundos, pero WireMock tardará 5 segundos.
        */
        /*
        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/api/pagos/validar/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withFixedDelay(5000) // Retraso intencional de 5 segundos
                        .withBody("{ \"status\": \"APPROVED\" }")));*/


        // ESCENARIO 3: Simular Caída del Banco

        /*wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/api/pagos/validar/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(500)
                        .withBody("{ \"error\": \"Internal Server Error en el Banco\" }")));
        */
    }

    public void apagarServidorMock(){
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

}
