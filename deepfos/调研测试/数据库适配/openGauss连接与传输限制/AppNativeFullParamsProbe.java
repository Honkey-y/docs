import com.alibaba.fastjson.JSONObject;
import com.merge.api.space.SpaceApi;
import com.merge.hikaridatasource.OsAppMergeDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.seeplnframework.hikaridatasource.HikariDataSourceInfo;
import org.seeplnframework.hikaridatasource.util.AESUtil;
import java.lang.reflect.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class AppNativeFullParamsProbe {
 private static final String ALL = "autoReconnect=true&useUnicode=true&allowMultiQueries=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&sessionVariables=max_allowed_packet%3D8388608&maxAllowedPacket=8388608";
 private static final String BASE = "autoReconnect=true&useUnicode=true&allowMultiQueries=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
 public static void main(String[] args) throws Exception {
  Properties lab = new Properties();
  try (var in = Files.newInputStream(Path.of(".env"))) { lab.load(in); }
  for (String options : new String[]{ALL, BASE}) {
   String label = options.equals(ALL) ? "EXACT_CUSTOMER_OPTIONS" : "WITHOUT_8M_MYSQL_OPTIONS";
   String original = "jdbc:mysql://127.0.0.1:13306/cz001?" + options;
   String key = "0123456789abcdef";
   HikariDataSourceInfo input = new HikariDataSourceInfo();
   input.setJdbcUrl(original); input.setDriverClassName("com.mysql.cj.jdbc.Driver");
   input.setUsername("lab_user"); input.setPassword(AESUtil.encrypt(key, lab.getProperty("GS_PASSWORD")));
   Class<?> apiResultType = SpaceApi.class.getMethod("getOsAppDataSource",String.class,String.class).getReturnType();
   Object apiResult = JSONObject.parseObject(JSONObject.toJSONString(input),apiResultType);
   SpaceApi spaceApi = (SpaceApi)Proxy.newProxyInstance(SpaceApi.class.getClassLoader(),new Class<?>[]{SpaceApi.class},(proxy,method,values)-> {
    if (method.getName().equals("getOsAppDataSource")) return apiResult;
    if (method.getName().equals("toString")) return "LocalLabSpaceApi";
    throw new UnsupportedOperationException(method.getName());
   });
   OsAppMergeDataSource routing = new OsAppMergeDataSource();
   set(routing,"spaceApi",spaceApi); set(routing,"datasourceMapperType","mysql-opengauss");
   set(routing,"openGaussDatabase","og_lab"); set(routing,"openGaussPort",15432); set(routing,"aesKey",key);
   Method method=OsAppMergeDataSource.class.getDeclaredMethod("getAppDataSource",String.class,String.class);method.setAccessible(true);
   HikariDataSourceInfo adapted=(HikariDataSourceInfo)method.invoke(routing,"lab-space","lab-app");
   if (!original.equals(input.getJdbcUrl())) throw new AssertionError("Source metadata changed");
   System.out.println("CASE="+label); System.out.println("URL="+adapted.getJdbcUrl());
   System.out.println("DRIVER="+adapted.getDriverClassName());
   try (HikariDataSource pool = new HikariDataSource()) {
    pool.setJdbcUrl(adapted.getJdbcUrl());pool.setDriverClassName(adapted.getDriverClassName());
    pool.setUsername(adapted.getUsername());pool.setPassword(adapted.getPassword());
    pool.setMaximumPoolSize(1);pool.setMinimumIdle(0);pool.setConnectionTimeout(10000);
    try(Connection c=pool.getConnection(); Statement s=c.createStatement()) {
     System.out.println("CONNECTION_CLASS="+c.unwrap(org.opengauss.PGConnection.class).getClass().getName());
     try(ResultSet r=s.executeQuery("SELECT current_database(),current_schema()")) {
      r.next();if(!"og_lab".equals(r.getString(1))||!"cz001".equals(r.getString(2)))throw new AssertionError("Incorrect destination");
      System.out.println("DESTINATION="+r.getString(1)+"."+r.getString(2));
     }
     try(ResultSet r=s.executeQuery("SHOW max_allowed_packet")) {
      r.next();System.out.println("SERVER_MAX_ALLOWED_PACKET="+r.getString(1));
      if(r.getInt(1)!=4194304)throw new AssertionError("Expected default server value of 4 MiB");
     }
     s.execute("CREATE TEMPORARY TABLE _codex_native_packet_probe (id integer, payload text)");
     for(int size:new int[]{4203408,9*1024*1024}) {
      try(PreparedStatement p=c.prepareStatement("INSERT INTO _codex_native_packet_probe VALUES (?,?)")) {
       p.setInt(1,size);p.setString(2,"x".repeat(size));if(p.executeUpdate()!=1)throw new AssertionError("Insert row count");
      }
      try(ResultSet r=s.executeQuery("SELECT octet_length(payload) FROM _codex_native_packet_probe WHERE id="+size)) {
       if(!r.next()||r.getInt(1)!=size)throw new AssertionError("Stored payload size mismatch");
       System.out.println("INSERT_AND_READBACK_BYTES="+r.getInt(1)+" PASS");
      }
     }
     s.execute("DROP TABLE _codex_native_packet_probe");
    }
   }
  }
 }
 private static void set(Object target,String name,Object value)throws Exception {
  Field field=target.getClass().getDeclaredField(name);field.setAccessible(true);field.set(target,value);
 }
}
