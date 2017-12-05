package com.example.app.whiteboardcoop.controller;

import com.example.app.whiteboardcoop.dto.WhiteboardDto;
import com.example.app.whiteboardcoop.model.User;
import com.example.app.whiteboardcoop.service.UserService;
import com.example.app.whiteboardcoop.service.WhiteboardService;
import com.example.app.whiteboardcoop.util.Constants;
import com.example.app.whiteboardcoop.util.PaginationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WhiteboardService whiteboardService;

    @Autowired
    private PaginationModel paginationModel;

    @GetMapping("/user/{username}/whiteboard/list")
    public String getUserWhiteboardsView(@PathVariable String username, Model model){
        Optional<User> user = userService.findByUsername(username);
        if(!user.isPresent()){
            return "page_not_exist";
        }
        model.addAttribute("searchedUsername", username);
        return "user_whiteboard_list";
    }

    @GetMapping("/user/{username}/whiteboard/list/active")
    public ResponseEntity<List<WhiteboardDto>> whiteboardsActiveList(@PathVariable String username,
                                                                     @RequestParam(required = false) Integer page){
        if(page == null) page = 0;

        Optional<User> user = userService.findByUsername(username);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        if(!user.isPresent()){
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        List<WhiteboardDto> whiteboardDtoList = whiteboardService.getNewestActiveWhiteboardsByUser(user.get(), page)
                .stream()
                .map(WhiteboardDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(whiteboardDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/user/{username}/whiteboard/list/inactive")
    public ResponseEntity<List<WhiteboardDto>> whiteboardsInactiveList(@PathVariable String username,
                                                                       @RequestParam(required = false) Integer page){
        if(page == null) page = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        Optional<User> user = userService.findByUsername(username);
        if(!user.isPresent()){
            return new ResponseEntity<List<WhiteboardDto>>(null, headers, HttpStatus.NOT_FOUND);
        }
        List<WhiteboardDto> whiteboardDtoList = whiteboardService.getNewestInactiveWhiteboardsByUser(user.get(), page)
                .stream()
                .map(WhiteboardDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<List<WhiteboardDto>>(whiteboardDtoList, headers, HttpStatus.OK);
    }

    @PostMapping("/user/find")
    public String findUser(@RequestParam String username){
        Optional<User> user = userService.findByUsername(username);
        return user.isPresent()
                ? "redirect:/user/" + username + "/whiteboard/list"
                : "user_not_exist";
    }

    @GetMapping("/user/getUsernames")
    @ResponseBody
    public List<String> getUsernames(@RequestParam String username){
        return userService.findAll().stream()
                .filter(x -> x.getUsername().toLowerCase().startsWith(username.toLowerCase()))
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{username}/getPagesNumber")
    @ResponseBody
    public Integer getPagesNumber(@PathVariable String username,
                                  @RequestParam boolean active){
        Optional<User> user = userService.findByUsername(username);
        if(!user.isPresent()) return 0;
        return active ? whiteboardService.getMaxActivePagesNumberPerUser(user.get())
                : whiteboardService.getMaxInactivePagesNumberPerUser(user.get());
    }

    @GetMapping("/user/{username}/getPagesInterval")
    @ResponseBody
    public List<Integer> getPagesInterval(@RequestParam Integer page,
                                          @RequestParam boolean active,
                                          @PathVariable String username){
        if(page == null) page = 0;
        Optional<User> user = userService.findByUsername(username);
        if(!user.isPresent()) return Collections.emptyList();

        return active ? paginationModel.generatePagesInterval(page, Constants.PAGE_INTERVAL, whiteboardService.getMaxActivePagesNumberPerUser(user.get()))
                : paginationModel.generatePagesInterval(page, Constants.PAGE_INTERVAL, whiteboardService.getMaxInactivePagesNumberPerUser(user.get()));
    }
}

