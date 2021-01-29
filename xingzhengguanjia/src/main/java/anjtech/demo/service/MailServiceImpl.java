package anjtech.demo.service;

import anjtech.demo.dao.MailRepository;
import anjtech.demo.po.Mail;
import anjtech.demo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private MailRepository mailRepository;

    @Override
    public Mail saveMail(Mail mail) {

        if (mail.getSend_status() == null) {
            mail.setSend_status(0);
        }

        return mailRepository.save(mail);
    }

    @Override
    public Page<Mail> listAll(Pageable pageable) {
        return mailRepository.findAll(pageable);
    }

    //    @Override
//    public Page<Mail> listMail(Long id, Pageable pageable) {
//        return mailRepository.findAll(new Specification<Mail>() {
//            @Override
//            public Predicate toPredicate(Root<Mail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.<User>get("user").get("id"), id);
//            }
//        }, pageable);
//    }

    @Override
    public void deleteMail(Long id) {
        mailRepository.deleteById(id);
    }


}
