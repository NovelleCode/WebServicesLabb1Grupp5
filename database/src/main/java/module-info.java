module database {
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    exports com.webservices.models;
}