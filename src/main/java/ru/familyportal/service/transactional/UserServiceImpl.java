package ru.familyportal.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.familyportal.model.dao.RoleDao;
import ru.familyportal.model.dao.UserDao;
import ru.familyportal.model.entity.Role;
import ru.familyportal.model.entity.User;
import ru.familyportal.service.UserService;
import ru.familyportal.utils.PasswordSupport;
import ru.familyportal.utils.email.MailService;
import ru.familyportal.utils.email.MailServiceInt;
import ru.familyportal.utils.email.templateEmail.AbstractMailBuilder;
import ru.familyportal.utils.email.templateEmail.AccountActivationMailBuilder;
import ru.familyportal.utils.email.templateEmail.MailTemplateService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 10.03.12
 * Time: 13:41
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private String login;
    private String activationLink = "";
    private ExecutorService sendMailExeService;
    private JavaMailSender mailSender;
    private String passwordHash;
    private List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
    //private ActivationAccountInformationTemplate activationTemplate = new ActivationAccountInformationTemplate();

    @Qualifier("userDaoImpl")
    @Autowired
    private UserDao userDao;

    @Qualifier("roleDaoImpl")
    @Autowired
    private RoleDao roleDao;


    @Qualifier("mailServiceMime")
    @Autowired
    private MailServiceInt mailService;

    @Qualifier("mailTemplateService")
    @Autowired
    private MailTemplateService mailTemplateService;

    @Qualifier("accountActivationMailBuilder")
    @Autowired
    private AbstractMailBuilder mailBuilder;

    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        if (this.mailSender != null) {
            this.sendMailExeService = Executors.newFixedThreadPool(5);
        }
    }

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getAppUserByName(s);

        if (user == null)
            throw new UsernameNotFoundException("No user with username '" + s + "' found!");

        for (Role role : user.getRoles()) {
            authList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(login, passwordHash, authList);
    }


    /**
     * This method add User to the data store
     * And if User is not active, then user entity
     * get the activation key and send to E-mail
     * of this created user
     * @param userDto
     * @return added User
     */

    public User addAppUser(User userDto) {
        if (userDto.isActive()) {
            userDto.setActivationKey(null);
        } else {
            userDto.setActivationKey(PasswordSupport.getBCryptPasswdHash(userDto.getLastName() + userDto.getUserName() + userDto.getEmail() + userDto.getFirstName()));
        }
        User user = userDao.createUser(userDto);
        if (!userDto.isActive()) {
            String email = user.getEmail();
            String activationKey = user.getActivationKey();
            if (activationLink != null && email != null && activationKey != null) {
                try {
                    String href = activationLink + ((activationLink.indexOf("?") != -1) ? "&act=" : "?act=") + activationKey;
                    //AbstractMailBuilder mailBuilder1 = new AccountActivationMailBuilder();
                    mailBuilder.setUser(user);
                    mailTemplateService.setMailBuilder(mailBuilder);
                    mailTemplateService.generateMassage();
                    //initMailTemplate(user, "efimalex83@gmail.com", "Activation accaunt");
                    //AbstractMailTemplate activationTemplate = new ActivationAccountInformationTemplate(user, "efimalex83@gmail.com", "Activation accaunt",href);
                    //sendL7dEmail(email, "account_activation_email", new Object[]{user.getUserName(), href}, Locale.getDefault());
                    mailService.sendMail(mailTemplateService.getSimpleMailMEssage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        Role userRole = roleDao.getRole("user");
        if (userRole == null) {
            userRole = roleDao.createRole("user", "General user role.");
        }
        user.addRole(userRole);
        userDao.save(user);
        return user;
    }

    public void updateUser(User userDto) {
        userDao.updateUser(userDto);
    }

    @Transactional(readOnly = true)
    public User getUser(String userName){
        return userDao.getAppUserByName(userName);
    }

    @Transactional(readOnly = true)
    public boolean hasUser(String userName) {
        return userDao.userExists(userName);
    }

    @Transactional(readOnly = true)
    public Role getRole(String roleName) {
        return roleDao.getRole(roleName);
    }

    public boolean deleteRole(Role role) {
        return roleDao.deleteRole(role);
    }

    public Role createRole(String roleName, String description) {
        return roleDao.createRole(roleName, description);
    }

    public Collection<User> getUsers(){
        return userDao.findAll();
    }

    public boolean deleteUser(User user){
        return userDao.remove(user);
    }

    public void resetPassword(String userName, String email) throws Exception {
        if (userName == null || email == null) return;
        User user = userDao.getAppUserByName(userName);
        if (user != null) {
            String userEmailOnRecord = user.getEmail();
            if (userEmailOnRecord != null && userEmailOnRecord.length() > 0) {
                if (userEmailOnRecord.equalsIgnoreCase(email.trim())) {
                    String tempPasswd = PasswordSupport.tempPassword();
                    user.setPasswordHash(PasswordSupport.getBCryptPasswdHash(tempPasswd));
                    user.setTemporaryPassword(true);
                    userDao.save(user);
                   // sendL7dEmail(userEmailOnRecord, "password_reset_email", new Object[]{user.getUserName(), tempPasswd}, Locale.getDefault());
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean isUserInRole(String userName, String roleName) {
        if (userName == null || roleName == null) return false;
        User user = userDao.getAppUserByName(userName);
        return user != null && user.hasRole(roleName);
    }

    public String activateUser(String activationKey) {
        return userDao.activateUser(activationKey);
    }

   /* protected void sendL7dEmail(String emailAddress, String l7dKey, Object[] l7dArgs, Locale locale) throws Exception {
        if (mailSender == null) return; // no mailSender configured ...
        ResourceBundle rb = ResourceBundle.getBundle("example.i18n", locale);
        String body = MessageFormat.format(rb.getString(l7dKey), l7dArgs);
        String subject = rb.getString(l7dKey + "_subject");
        body = body.trim();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.addTo(new InternetAddress(emailAddress));
        helper.setSubject((subject != null ? subject : ""));
        helper.setText(body, true);
        sendMailExeService.execute(new SendMailRunnable(message, mailSender));
    }

    class SendMailRunnable implements Runnable {
        private MimeMessage message;
        private JavaMailSender mailSender;

        SendMailRunnable(MimeMessage message, JavaMailSender mailSender) {
            this.message = message;
            this.mailSender = mailSender;
        }

        public void run() {
            try {
                if (mailSender != null && message != null) {
                    mailSender.send(message);
                }
            } catch (Throwable zzz) {
                zzz.printStackTrace(); // todo: log it properly
            }
        }
    }*/
}
