import com.sun.net.httpserver.HttpHandler;
import com.webservices.httphandler.DatabaseHttpHandler;
import com.webservices.httphandler.FilesHttpHandler;
import com.webservices.plugin.Route;

module core {
    uses com.sun.net.httpserver.HttpHandler;
    uses Route;
    requires com.webservices.fileutils;
    requires jdk.httpserver;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires database;
    requires plugin;
    provides HttpHandler with DatabaseHttpHandler, FilesHttpHandler;

}