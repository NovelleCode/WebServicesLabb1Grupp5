import com.sun.net.httpserver.HttpHandler;
import com.webservices.httphandler.FilesHttpHandler;
import com.webservices.annotations.Route;

module core {
    uses com.sun.net.httpserver.HttpHandler;
    uses Route;
    requires com.webservices.utils;
    requires jdk.httpserver;
    requires org.apache.commons.lang3;
    provides HttpHandler with FilesHttpHandler;

}