package net.trajano.ms.engine.sample;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class SomeAppScope implements
    ISomeAppScope {

    private final int i = ThreadLocalRandom.current().nextInt();

    @Override
    public int get() {

        return i;
    }

    @PostConstruct
    public void init() {

        System.out.println("FFFFA");
    }
}
