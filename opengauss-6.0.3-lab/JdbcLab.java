import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.jdbc.JdbcConnection;
import java.sql.*;

public class JdbcLab {
    private static final String MYSQL = "jdbc:mysql://127.0.0.1:13306/cz001"
            + "?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    private static final String NATIVE = "jdbc:opengauss://127.0.0.1:15432/og_lab"
            + "?currentSchema=cz001&sslmode=disable";
    private static final String PAYLOAD = "x".repeat(4_203_408);

    public static void main(String[] args) throws Exception {
        System.out.println("PAYLOAD_ASCII_BYTES=" + PAYLOAD.length());
        try (Connection c = connect(MYSQL)) {
            inspect("mysql-default", c);
            insert("mysql-default", c, 10);
            execute(c, "SET max_allowed_packet = 8388608");
            inspect("mysql-manual-set", c);
            insert("mysql-manual-set", c, 11);
            c.unwrap(JdbcConnection.class).getPropertySet()
                    .getIntegerProperty(PropertyKey.maxAllowedPacket).setValue(8_388_608);
            inspect("mysql-manual-set-and-driver", c);
            insert("mysql-manual-set-and-driver", c, 12);
        }
        try (Connection c = connect(MYSQL
                + "&sessionVariables=max_allowed_packet%3D8388608&maxAllowedPacket=8388608")) {
            inspect("mysql-url-settings", c);
            insert("mysql-url-settings", c, 13);
        }
        try (Connection c = connect(NATIVE)) {
            inspect("native-existing-B-db", c);
            query(c, "SELECT id, value FROM packet_probe WHERE id=1");
            insert("native-existing-B-db", c, 14);
            System.out.println("native-original-mysql-mapper-sql:");
            execute(c, "CREATE TABLE IF NOT EXISTS appcz001_element_content "
                    + "(element_info_id varchar(64), `key` varchar(64), `value` text)");
            try (PreparedStatement p = c.prepareStatement("INSERT INTO `appcz001_element_content` "
                    + "(`element_info_id`,`key`,`value`) VALUES (?,?,?)")) {
                p.setString(1, "native-probe"); p.setString(2, "large-content"); p.setString(3, PAYLOAD);
                System.out.println("native-mysql-insert-rows=" + p.executeUpdate());
            }
            query(c, "SELECT length(`value`) FROM `appcz001_element_content` WHERE element_info_id='native-probe'");
            execute(c, "DELETE FROM appcz001_element_content WHERE element_info_id='native-probe'");
            execute(c, "ALTER ROLE lab_user SET max_allowed_packet = 8388608");
        }
        try (Connection c = connect(MYSQL)) {
            inspect("mysql-after-alter-role", c);
            insert("mysql-after-alter-role", c, 15);
        }
        try (Connection c = connect(NATIVE)) {
            execute(c, "ALTER ROLE lab_user RESET max_allowed_packet");
        }
        try (Connection c = connect("jdbc:opengauss://127.0.0.1:15432/cz001?sslmode=disable")) {
            inspect("native-naive-database-cz001", c);
        } catch (SQLException e) {
            System.out.println("native-naive-database-cz001=FAIL " + shortError(e));
        }
    }

    private static Connection connect(String url) throws SQLException {
        return DriverManager.getConnection(url, "lab_user", System.getenv("GS_PASSWORD"));
    }

    private static void inspect(String label, Connection c) throws SQLException {
        System.out.println("\nCASE=" + label);
        System.out.println("driver=" + c.getMetaData().getDriverName() + ":" + c.getMetaData().getDriverVersion());
        query(c, "SELECT current_database(), current_schema()");
        query(c, "SHOW search_path");
        query(c, "SELECT table_schema FROM information_schema.tables WHERE table_name='packet_probe'");
        query(c, "SHOW max_allowed_packet");
        query(c, "SELECT @@max_allowed_packet");
        if (c.isWrapperFor(JdbcConnection.class)) {
            JdbcConnection jdbc = c.unwrap(JdbcConnection.class);
            System.out.println("driver.sessionVariables=" + jdbc.getPropertySet()
                    .getStringProperty(PropertyKey.sessionVariables).getValue());
            System.out.println("driver.cachedMaxAllowedPacket=" + jdbc.getSession()
                    .getServerSession().getServerVariable("max_allowed_packet"));
            System.out.println("driver.effectiveMaxAllowedPacket=" + jdbc.getPropertySet()
                    .getIntegerProperty(PropertyKey.maxAllowedPacket).getValue());
        }
    }

    private static void query(Connection c, String sql) {
        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder row = new StringBuilder(sql + " => ");
                for (int i = 1; i <= columns; i++) {
                    if (i > 1) row.append(" | ");
                    row.append(rs.getString(i));
                }
                System.out.println(row);
            }
        } catch (SQLException e) {
            System.out.println(sql + " => ERROR " + shortError(e));
        }
    }

    private static void execute(Connection c, String sql) throws SQLException {
        try (Statement s = c.createStatement()) { s.execute(sql); }
    }

    private static void insert(String label, Connection c, int id) {
        try (PreparedStatement p = c.prepareStatement("INSERT INTO packet_probe(id,value) VALUES (?,?)")) {
            p.setInt(1, id); p.setString(2, PAYLOAD);
            System.out.println(label + ".insert=PASS rows=" + p.executeUpdate());
            query(c, "SELECT length(value) FROM packet_probe WHERE id=" + id);
            execute(c, "DELETE FROM packet_probe WHERE id=" + id);
        } catch (SQLException e) {
            System.out.println(label + ".insert=FAIL " + shortError(e));
        }
    }

    private static String shortError(SQLException e) {
        String message = e.getMessage();
        if (message == null) message = "";
        return e.getClass().getSimpleName() + "/" + e.getSQLState() + ": "
                + message.substring(0, Math.min(600, message.length())).replace('\n', ' ');
    }
}
