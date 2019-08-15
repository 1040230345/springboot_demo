package com.jackson.springboot_quick.controller;

import com.jackson.springboot_quick.dto.AccessTokenDTO;
import com.jackson.springboot_quick.dto.GithubUser;
import com.jackson.springboot_quick.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${Client_id}")
    private String Client_id;
    @Value("${Client_secret}")
    private String Client_secret;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //赋值
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        //调用获取accessToken
        String token = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(token);
        //获取姓名等资料
        GithubUser githubUser = githubProvider.getUser(token);
        System.out.println(githubUser.getBio());
        return "index";
    }
}
