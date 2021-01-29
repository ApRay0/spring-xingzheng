package anjtech.demo.util;

import anjtech.demo.config.EmailConfig;
import anjtech.demo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Component
public class EmailTask {

    @Autowired
    EmailSender emailSender;

    @Value("${spring.mail.username}")
    private String EMAILUSER;




    @Value("${spring.datasource.driver-class-name}")
    private String JDBC_DRIVER;
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    public void GetMailTask() {
        Connection conn = null;
        Statement stmt = null;


        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql;
            sql = "select * from t_mail";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String send_to = rs.getString("send_to");
                String send_from = rs.getString("send_from");
                Date send_date = rs.getDate("send_date");
                String head = rs.getString("head");
                String content = rs.getString("content");



                Date cur_date = new Date();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String res_date = fmt.format(cur_date).toString();
                String res_date2 = send_date.toString();
                if (res_date.equals(res_date2)) {
                    emailSender.sendEmail(send_from, EMAILUSER, head, content);

                }
            }
            rs.close();
            stmt.close();
            conn.close();


        } catch (ClassNotFoundException | SQLException | MessagingException e) {
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
