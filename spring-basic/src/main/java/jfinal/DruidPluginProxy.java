package jfinal;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Created by crabime on 6/29/17.
 */
public class DruidPluginProxy {
    private String dataSourceName;
    private String dataSourceType;
    private Dialect databaseDialect;
    private DruidPlugin plugin = null;

    public DruidPluginProxy(String dataSourceName, String url, String username, String password, String driverClass){
        this.dataSourceName = dataSourceName;
        this.plugin = new DruidPlugin(url, username, password, driverClass);
        this.addFilter(url);
    }

    public void addFilter(String url){
        this.dataSourceType = this.getDbType(url); //返回JdbcConstants.MYSQL
        if (JdbcConstants.ORACLE.equals(dataSourceType)){
            this.databaseDialect = new OracleDialect();
        }else if(JdbcConstants.MYSQL.equals(dataSourceType)){
            this.databaseDialect = new MysqlDialect();
        }
        StatFilter statFilter = new StatFilter();
        statFilter.setMergeSql(true);
        this.plugin.addFilter(statFilter);

        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType(this.dataSourceType);
        this.plugin.addFilter(wallFilter);
    }

    /**
     * 暂且只定义mysql与oracle
     * @param url 驱动名称
     * @return 数据库类型
     */
    public String getDbType(String url){
        if (url.startsWith("jdbc:oracle")){
            return JdbcConstants.ORACLE;
        } else if(url.startsWith("jdbc:mysql")){
            return JdbcConstants.MYSQL;
        }
        return null;
    }

    public void start(){
        this.plugin.start();
    }

    public void stop(){
        this.plugin.stop();
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public Dialect getDatabaseDialect() {
        return databaseDialect;
    }

    public DruidPlugin getPlugin() {
        return plugin;
    }
}
