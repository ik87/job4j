package ru.job4j.parser;

import java.util.Properties;
import java.util.TimeZone;

public class Config implements Cloneable{

    private String site;
    private String job;

    private String driver;
    private String subUrl;
    private String username;
    private String password;

    private boolean siteState;
    private boolean jobState;
    private String cron;
    private String filterTable;
    private String filterPage;
    private Long parseWith;
    private TimeZone timeZone;
    private Properties prop;


    public boolean setSite(int num) {
        clearSite();
        String name = "site" + num;
        this.site = prop.getProperty(name);
        siteState = "on".equals(prop.getProperty(site + ".state"));
        return prop.containsKey(name);
    }

    public boolean setJob(int num) {
        clearJob();
        String name = site + ".job" + num + ".state";
        this.job = ".job" + num;
        this.jobState = "on".equals(prop.getProperty(name));
        return prop.containsKey(name);
    }

    public void setProperties() {
        setDB();
        setSettings();
        time();
    }

    private void setDB() {
        driver = prop.getProperty("jdbc.driver");
        subUrl = prop.getProperty("jdbc.url");
        username = prop.getProperty("jdbc.username");
        password = prop.getProperty("jdbc.password");
    }

    private void setSettings() {
        cron = prop.getProperty(site + job + ".cron");
        filterTable = prop.getProperty(site + job + ".filter_table");
        filterPage = prop.getProperty(site + job + ".filter_page");
        filterPage = prop.getProperty(site + job + ".sub_url");
    }

    private void time() {
        String time = prop.getProperty(site + job + ".parse_with");
        String timeZone = prop.getProperty(site + job + ".time_zone");

        if (time != null && timeZone != null) {
            this.timeZone = TimeZone.getTimeZone(timeZone);
            parseWith = new Utils().dateToMillis(time, this.timeZone);
        }

    }

    private void clearJob() {
        jobState = false;
        cron = null;
        filterTable = null;
        filterPage = null;
        parseWith = null;
        timeZone = null;
    }

    private void clearSite() {
        site = null;
        siteState = false;
        clearJob();
    }

    @Override
    public Config clone() {
        try
        {
            Config config = (Config) super.clone();
            // if you have custom object, then you need create a new one in here
            return config ;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new Config();
        }
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSiteState() {
        return siteState;
    }

    public void setSiteState(boolean siteState) {
        this.siteState = siteState;
    }

    public boolean isJobState() {
        return jobState;
    }

    public void setJobState(boolean jobState) {
        this.jobState = jobState;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getFilterTable() {
        return filterTable;
    }

    public void setFilterTable(String filterTable) {
        this.filterTable = filterTable;
    }

    public String getFilterPage() {
        return filterPage;
    }

    public void setFilterPage(String filterPage) {
        this.filterPage = filterPage;
    }

    public Long getParseWith() {
        return parseWith;
    }

    public void setParseWith(Long parseWith) {
        this.parseWith = parseWith;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

}
