package net.trajano.ms.gateway.test;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;

import org.junit.Test;

import net.trajano.ms.gateway.providers.Handlers;

public class UriTest {

    @Test
    public void testOriginWithPath() throws Exception {

        assertEquals(URI.create("https://bugzilla.mozilla.org"), Handlers.getPartsForOriginHeader(new URL("https://bugzilla.mozilla.org/show_bug.cgi?id=446344#c97")));
    }

    @Test
    public void testOriginWithPort() throws Exception {

        assertEquals(URI.create("https://bugzilla.mozilla.org:3423"), Handlers.getPartsForOriginHeader(new URL("https://bugzilla.mozilla.org:3423/show_bug.cgi?id=446344#c97")));
    }

    @Test
    public void testOriginWithSlashPath() throws Exception {

        assertEquals(URI.create("https://bugzilla.mozilla.org"), Handlers.getPartsForOriginHeader(new URL("https://bugzilla.mozilla.org/")));
    }

    @Test
    public void testSimpleOrigin() throws Exception {

        assertEquals(URI.create("https://bugzilla.mozilla.org"), Handlers.getPartsForOriginHeader(new URL("https://bugzilla.mozilla.org")));
    }
}
