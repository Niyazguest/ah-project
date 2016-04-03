package ru.niyaz.test.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;
import ru.niyaz.test.dao.MessageDao;
import ru.niyaz.test.dao.UserDao;
import ru.niyaz.test.entity.Message;
import ru.niyaz.test.entity.User;
import ru.niyaz.test.security.UserDetailsImpl;
import ru.niyaz.test.util.ThymeleafTemplateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 02.04.16.
 */

@Controller
public class MainController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(value = "/myPage", method = RequestMethod.GET)
    public void getMyPage(@RequestParam(value = "code", required = false) String vkCode,
                          HttpServletRequest request, HttpServletResponse response) {
        try {
            String token;
            String userID;
            if (vkCode != null) {
                URL url = new URL("https://oauth.vk.com/access_token?client_id=5394025&client_secret=Yle0IIsoPKOKBiY3rGoI&redirect_uri=http://localhost:8080/testapp/myPage&code=" + vkCode);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String s = bf.readLine();
                JSONObject jsonObject = new JSONObject(s);
                token = (String) jsonObject.get("access_token");
                userID = (String) jsonObject.get("user_id");
            }
            response.setContentType("text/html;charset=UTF-8");
            WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
            ThymeleafTemplateUtil.getTemplateEngine().process("main", webContext, response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void editProfile(@RequestParam(value = "username", required = false) String name,
                            @RequestParam(value = "password", required = false) String password,
                            @RequestParam(value = "vkPage", required = false) String vkPage,
                            @RequestParam(value = "want", required = false) Boolean want,
                            @RequestParam(value = "active", required = false) Boolean active,
                            HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCurrentUser();
            user.setName(name);
            user.setPassword(password);
            user.setVkPage(vkPage);
            user.setWant(want);
            user.setActive(active);
            userDao.saveUser(user);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "peoples/{placeID}", method = RequestMethod.GET)
    public void getPeoplesPage(@PathVariable String placeID,
                               HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCurrentUser();
            user.setCurrentPlaceId(placeID);
            userDao.saveUser(user);
            List<User> users = userDao.getUsersByPlace(placeID);
            WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
            webContext.setVariable("peoples", users);
            ThymeleafTemplateUtil.getTemplateEngine().process("main", webContext, response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "chat", method = RequestMethod.GET)
    public void getChat(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
            ThymeleafTemplateUtil.getTemplateEngine().process("chat", webContext, response.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "message", method = RequestMethod.POST)
    public void send(@RequestParam(value = "message") String message,
                     @RequestParam(value = "username") String username,
                     HttpServletRequest request, HttpServletResponse response) {
        Long id = messageDao.saveMessage(message);
        String text = "                      <li id=\"" + id.toString() + "\" class=\"left clearfix\">\n" +
                "                            <div class=\"chat-body clearfix\">\n" +
                "                                <div class=\"header\">\n" +
                "                                    <small class=\" text-muted\"><span class=\"glyphicon glyphicon-time\"></span>" + new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date()) + "</small>\n" +
                "                                    <strong class=\"pull-right primary-font\">" + username + "</strong>\n" +
                "                                </div>\n" +
                "                                <p>\n" +
                message +
                "                                </p>\n" +
                "                            </div>\n" +
                "                        </li>";
        try {
            response.getOutputStream().print(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @RequestMapping(value = "getMessages", method = RequestMethod.GET)
    public void getMessages(Long id,
                            HttpServletRequest request, HttpServletResponse response) {

        List<Message> messages = messageDao.getMessages(id);
        String result = "";
        for (Message message : messages) {
            String text = "                        <li id=\"" + message.getId().toString() + "\" class=\"left clearfix\">\n" +
                    "                            <div class=\"chat-body clearfix\">\n" +
                    "                                <div class=\"header\">\n" +
                    "                                    <small class=\" text-muted\"><span class=\"glyphicon glyphicon-time\"></span>" + new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(message.getDate()) + "</small>\n" +
                    "                                    <strong class=\"pull-right primary-font\"></strong>\n" +
                    "                                </div>\n" +
                    "                                <p>\n" +
                    message.getMessage() +
                    "                                </p>\n" +
                    "                            </div>\n" +
                    "                        </li>";
            result = result + text;
        }
        try {
            response.getOutputStream().print(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
