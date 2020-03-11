package per.lee.bravo.mall.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.mall.authorization.service.IRoleIssueService;
import per.lee.bravo.mall.authorization.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected IUserService userService;
    @Autowired
    protected IRoleIssueService roleIssueService;

}
