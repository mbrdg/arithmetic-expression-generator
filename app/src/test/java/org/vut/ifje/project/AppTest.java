package org.vut.ifje.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasUsage() {
        assertNotNull(App.usage("generator"), "app should have a static usage method");
    }
}
