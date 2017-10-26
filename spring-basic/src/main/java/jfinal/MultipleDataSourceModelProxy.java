package jfinal;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by crabime on 6/29/17.
 */
public class MultipleDataSourceModelProxy {
    private List<String> modelPackages = null;
    private DruidPluginProxy druidPluginProxy = null;
    private EhCachePlugin ehCachePlugin = null;
    private ActiveRecordPlugin activeRecordPlugin = null;

    /**
     * 在Spring容器通过构造注入的方式扫描指定包下的带有注解的文件
     *
     * @param druidPluginProxy 多数据源配置
     * @param modelPackages    工程目录下需要扫描的那些包
     */
    public MultipleDataSourceModelProxy(DruidPluginProxy druidPluginProxy, List<String> modelPackages) {
        this.druidPluginProxy = druidPluginProxy;
        this.modelPackages = modelPackages;
    }

    public void startup() {
        activeRecordPlugin = new ActiveRecordPlugin(druidPluginProxy.getDataSourceName(), druidPluginProxy.getPlugin());
        activeRecordPlugin.setShowSql(true);
        activeRecordPlugin.setDevMode(false);
        activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(false));
        activeRecordPlugin.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        activeRecordPlugin.setDialect(druidPluginProxy.getDatabaseDialect());
        activeRecordPlugin.setBaseSqlTemplatePath(PathKit.getRootClassPath());
        activeRecordPlugin.addSqlTemplate("address.sql");
        //增加缓存处理
        ehCachePlugin = new EhCachePlugin();
        ehCachePlugin.start();

        for (int i = 0; i < modelPackages.size(); i++) {
            String modelPackage = modelPackages.get(i);
            Set<Class<?>> classes = ClassFunction.getClasses(modelPackage);
            if (null != classes && classes.size() > 0) {
                Iterator<Class<?>> it = classes.iterator();
                while (it.hasNext()) {
                    Class<?> modelClass = it.next();
                    JFinalTableAnnotation table = modelClass.getAnnotation(JFinalTableAnnotation.class);
                    //判断com.jfinal.plugin.activerecord.Model class是否是modelClass的父类或者父接口
                    if (null != table && Model.class.isAssignableFrom(modelClass)) {
                        String tableName = table.tableName();
                        String primaryKey = table.primaryColumn();
                        if (!StringUtils.isEmpty(primaryKey)) {
                            activeRecordPlugin.addMapping(tableName, primaryKey, (Class<? extends Model<?>>) modelClass);
                        } else {
                            activeRecordPlugin.addMapping(tableName, (Class<? extends Model<?>>) modelClass);
                        }
                    }
                }
            }
        }
    }

    public void start() {
        this.startup();
        this.startDataSource();
        this.startActiveRecord();
    }

    public void startDataSource() {
        druidPluginProxy.start();
    }

    public void startActiveRecord() {
        activeRecordPlugin.start();
    }

    public void stop() {
        this.stopDataSource();
        this.stopActiveRecord();
    }

    public void stopDataSource() {
        druidPluginProxy.stop();
        //关闭缓存插件
        ehCachePlugin.stop();
    }

    public void stopActiveRecord() {
        activeRecordPlugin.stop();
    }
}
