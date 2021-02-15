package com.webservices.plugin;

import com.webservices.spi.Page;

public class ExternalPage implements Page {
    @Override
    public void execute() {
        System.out.println("EXTERNAL PAGE");
    }
}
