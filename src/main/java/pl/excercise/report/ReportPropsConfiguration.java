package pl.excercise.report;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.util.Properties;

@Singleton
@Startup
public class ReportPropsConfiguration {

    Properties properties = new Properties();

    @PostConstruct
    public void loadProperties() {
        try {
            properties.load(getClass().getResourceAsStream("/report.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("report.properties file not found");
        }
    }

    public String getReportHost() {
        return properties.getProperty("report.host");
    }

    public Integer getReportHostPort() {
        return Integer.valueOf(properties.getProperty("report.port"));
    }

    @Override
    public String toString() {
        return "ReportPropsConfiguration{" +
                "serverAddress='" + getReportHost()+ '\'' +
                ", port=" + getReportHostPort() +
                '}';
    }
}
