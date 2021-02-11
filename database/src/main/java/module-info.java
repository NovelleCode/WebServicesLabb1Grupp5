module database {
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    exports com.webservices.models;
    opens com.webservices.models to org.hibernate.orm.core, com.google.gson;

}