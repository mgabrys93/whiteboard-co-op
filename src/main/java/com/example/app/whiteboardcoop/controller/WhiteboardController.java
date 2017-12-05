package com.example.app.whiteboardcoop.controller;

import com.example.app.whiteboardcoop.dto.WhiteboardDto;
import com.example.app.whiteboardcoop.model.Board;
import com.example.app.whiteboardcoop.model.User;
import com.example.app.whiteboardcoop.model.Whiteboard;
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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/whiteboard")
public class WhiteboardController {

    @Autowired
    private WhiteboardService whiteboardService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaginationModel paginationModel;

    @GetMapping("/new")
    public String whiteboard(Principal principal){
        Optional<User> user = userService.findByUsername(principal.getName());
        if(!user.isPresent()) {
            return "page_not_exist";
        }
        Whiteboard whiteboard = new Whiteboard(user.get());
        whiteboard.setCreationDateTime(LocalDateTime.now());
        whiteboardService.save(whiteboard);
        return "redirect:/whiteboard/" + whiteboard.getId() + "/edit";

    }

    @GetMapping("/{whiteboardId}/img")
    public ResponseEntity<byte[]> getImage(@PathVariable Long whiteboardId){
        Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        if(!whiteboard.isPresent()) {
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        byte[] media = whiteboard.get().getImage();
        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

    @GetMapping("/{whiteboardId}/show")
    public String showWhiteboard(@PathVariable Long whiteboardId, Model model){
        Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
        if(!whiteboard.isPresent()){
            return "page_not_exist";
        }
        model.addAttribute("whiteboardId", whiteboard.get().getId());
        return "index";
    }

    @GetMapping("/{whiteboardId}/edit")
    public String edit(@PathVariable Long whiteboardId, Model model, Principal principal){
        Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
        if(!whiteboard.isPresent()){
            return "page_not_exist";
        }

        boolean isOwner = principal.getName().equals(whiteboard.get().getOwner().getUsername());
        if(!isOwner){
            return "access_denied";
        }
        model.addAttribute("whiteboardId", whiteboardId);

        boolean isEdited = whiteboard.get().getImage() != null;
        model.addAttribute("isEdited", isEdited);
        return "whiteboard";
    }

    @GetMapping("/{whiteboardId}/join")
    public String join(@PathVariable Long whiteboardId, Model model, Principal principal){

        Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
        if(!whiteboard.isPresent()){
            return "page_not_exist";
        }

        model.addAttribute("whiteboardId", whiteboardId);
        return whiteboard.get().isActive()
                ? "subwhiteboard"
                : "redirect:/whiteboard/" + whiteboardId + "/show";

    }

    @PostMapping("/{whiteboardId}/save")
    @ResponseBody
    public void save(@PathVariable Long whiteboardId, @RequestBody Board board){
        Optional<Whiteboard> whiteboard = whiteboardService.findOne(whiteboardId);
        whiteboard.get().setImage(board.getDataUrl().getBytes());
        whiteboardService.update(whiteboard.get());
    }

    @GetMapping("/list")
    public String whiteboardList(@RequestParam(required = false) Integer page, Model model){
        if(page == null) page = 0;
        int activePagesNumber = whiteboardService.getMaxActivePagesNumber();
        int inactivePagesNumber = whiteboardService.getMaxInactivePagesNumber();
        model.addAttribute("activePagesNumber", activePagesNumber);
        model.addAttribute("inactivePagesNumber", inactivePagesNumber);
        model.addAttribute("activePageInterval", paginationModel.generatePagesInterval(page, Constants.PAGE_INTERVAL, activePagesNumber));
        model.addAttribute("inactivePageInterval", paginationModel.generatePagesInterval(page, Constants.PAGE_INTERVAL, inactivePagesNumber));
        return "whiteboard_list";
    }

    @GetMapping("/list/inactive")
    public ResponseEntity<List<WhiteboardDto>> whiteboardInactiveList(@RequestParam(required = false) Integer page, Model model){
        if(page == null) page = 0;

        List<WhiteboardDto> whiteboardDtoList = whiteboardService.getNewestInactiveWhiteboards(page)
                .stream()
                .map(WhiteboardDto::new)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(whiteboardDtoList,headers, HttpStatus.OK);

    }

    @GetMapping("/list/active")
    public ResponseEntity<List<WhiteboardDto>> whiteboardActiveList(@RequestParam(required = false) Integer page, Model model){
        if(page == null) page = 0;

        List<WhiteboardDto> whiteboardDtoList = whiteboardService.getNewestActiveWhiteboards(page)
                .stream()
                .map(WhiteboardDto::new)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(whiteboardDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/getPagesNumber")
    @ResponseBody
    public Integer getPagesNumber(@RequestParam boolean active){
        return  active ? whiteboardService.getMaxActivePagesNumber() : whiteboardService.getMaxInactivePagesNumber();
    }

    @GetMapping("/getPagesInterval")
    @ResponseBody
    public List<Integer> getPagesInterval(@RequestParam Integer page,
                                          @RequestParam boolean active){
        if(page == null) page = 0;
        return active ? paginationModel.generatePagesInterval(page, Constants.PAGE_INTERVAL, whiteboardService.getMaxActivePagesNumber())
                : paginationModel.generatePagesInterval(page, Constants.PAGE_INTERVAL, whiteboardService.getMaxInactivePagesNumber());
    }
}

