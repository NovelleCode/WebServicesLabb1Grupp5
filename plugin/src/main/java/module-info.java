import com.sun.net.httpserver.HttpHandler;
import com.webservices.plugin.ExternalPage;
import com.webservices.plugin.PluginHttpHandler;
import com.webservices.spi.Page;

module plugin {
    requires spi;
    requires jdk.httpserver;

    provides Page with ExternalPage;
    provides HttpHandler with PluginHttpHandler;

}