package anjtech.demo.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String head;
    private String send_from;
    private String send_to;
    private String content;

    @Temporal(TemporalType.DATE)
    private Date send_date;

    @ManyToOne
    private User user;

    private Integer send_status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSend_from() {
        return send_from;
    }

    public void setSend_from(String send_from) {
        this.send_from = send_from;
    }

    public String getSend_to() {
        return send_to;
    }

    public void setSend_to(String send_to) {
        this.send_to = send_to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSend_date() {
        return send_date;
    }

    public void setSend_date(Date send_date) {
        this.send_date = send_date;
    }

    public Integer getSend_status() {
        return send_status;
    }

    public void setSend_status(Integer send_status) {
        this.send_status = send_status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
