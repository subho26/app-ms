package net.trajano.ms.example.authn;

import javax.ws.rs.core.Application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.trajano.ms.common.Microservice;

@SpringBootApplication
@EnableScheduling
public class SampleAuthn extends Application {

    public static void main(final String[] args) {

        Microservice.run(SampleAuthn.class, args);
    }
}
