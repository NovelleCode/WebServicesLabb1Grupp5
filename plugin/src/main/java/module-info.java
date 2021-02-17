import com.sun.net.httpserver.HttpHandler;
import com.webservices.plugin.DatabaseHttpHandler;
import com.webservices.plugin.PluginHttpHandler;

module plugin {
    requires jdk.httpserver;
    requires org.apache.commons.lang3;
    requires database;
    requires com.webservices.utils;

    provides HttpHandler with PluginHttpHandler, DatabaseHttpHandler;
}