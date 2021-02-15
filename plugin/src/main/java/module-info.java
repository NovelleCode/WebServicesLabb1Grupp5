import com.webservices.plugin.ExternalPage;
import com.webservices.spi.Page;

module plugin {
    requires spi;

    provides Page with ExternalPage;

}