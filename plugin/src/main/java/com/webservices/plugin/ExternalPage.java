package com.webservices.plugin;

import com.webservices.spi.Page;

@Route("/test")
public class ExternalPage implements Page {

    @Override
    public void execute() {
        System.out.println("EXTERNAL PAGE");
    }
}
