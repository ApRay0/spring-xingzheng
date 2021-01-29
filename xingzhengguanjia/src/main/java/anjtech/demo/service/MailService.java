package anjtech.demo.service;


import anjtech.demo.po.Mail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MailService {
    Mail saveMail(Mail mail);

    Page<Mail> listAll(Pageable pageable);

    void deleteMail(Long id);
}
