// class name : com.zaxxer.hikari.HikariConfig
// method name: validate()
// side       : merge
// region     : lines 577-582 (affected lines: [577, 578, 579, 580, 581, 582])
class HikariConfig {
    void validate__() {
        if (jdbcUrl != null)
        {
            logger.info("Really, a JDBC URL?  It's time to party like it's 1999!");
            dataSource = new DriverDataSource(jdbcUrl, dataSourceProperties);
        }
        else if (dataSource == null && dataSourceClassName == null)
    }
}
