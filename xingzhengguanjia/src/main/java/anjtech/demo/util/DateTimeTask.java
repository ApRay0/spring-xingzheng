package anjtech.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateTimeTask {

    @Value("${spring.datasource.driver-class-name}")
    private String JDBC_DRIVER;
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    public void DateTask() {
        Connection conn = null;
        Statement stmt = null;


        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql;
            sql = "select id ,end_time from t_task";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Long id = rs.getLong("id");
                String end_time = rs.getString("end_time");
                Date cur_date = new Date();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String res_date = fmt.format(cur_date).toString();
                String res_date2 = end_time.toString();


                if (res_date.equals(res_date2)) {
                    PreparedStatement psql = conn.prepareStatement("update t_task set finished = 1 where id = ?");
                    psql.setLong(1, id);
                    psql.execute();
                    psql.close();
                }
            }
            rs.close();
            stmt.close();
            conn.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

}
