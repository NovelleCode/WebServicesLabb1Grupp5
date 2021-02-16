import com.sun.net.httpserver.HttpHandler;
import com.webservices.plugin.PluginHttpHandler;

module plugin {
    requires jdk.httpserver;
    provides HttpHandler with PluginHttpHandler;
    exports com.webservices.plugin;

}